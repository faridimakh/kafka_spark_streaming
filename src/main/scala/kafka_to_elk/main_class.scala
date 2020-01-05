package kafka_to_elk

import common_tools.vals._
import kafka_to_elk.kafka_tune_params._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.elasticsearch.spark.sql._

object main_class {
  def main(args: Array[String]): Unit = {

    val streamingContext = new StreamingContext(spark.sparkContext, Seconds(1))
    Logger.getLogger("org").setLevel(Level.ERROR)
    import spark.implicits._
    val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      streamingContext, locationStrategy = PreferConsistent, consumerStrategy = Subscribe[String, String](Array("velib-stations"), kafkaParams))
    //reccuperer le message
    val message: DStream[String] = stream.map(x => x.value)
    message.foreachRDD(x => {
      val row_to_dataSet: Dataset[String] = x.toDS()
      var row_to_dataSet_parsed: DataFrame = row_to_dataSet
        .withColumn("structuredColumn", from_json(col("value"), schema_valid)).drop("value")
      coloumn_vilib_api.foreach(x => row_to_dataSet_parsed = row_to_dataSet_parsed.withColumn(x, col("structuredColumn." + x)))
      //pour voir un changement remarquable de données j'ai rajouté (vélos) j'ai rajouté deux colume qui change continuellement 
      row_to_dataSet_parsed = row_to_dataSet_parsed.withColumn("random_col", rand() * 3)
      row_to_dataSet_parsed = row_to_dataSet_parsed.withColumn("random_col2", rand() * 3)
      row_to_dataSet_parsed = row_to_dataSet_parsed.drop("structuredColumn")
      row_to_dataSet_parsed.saveToEs("vilib/1", Map("es.mapping.id" -> "name"))
    })
    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
