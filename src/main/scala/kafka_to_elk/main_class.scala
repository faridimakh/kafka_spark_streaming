package kafka_to_elk

import common_tools.vals._
import kafka_to_elk.elk_kafka_tools._
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.{Seconds, StreamingContext}

object main_class {
  def main(args: Array[String]): Unit = {

    val streamingContext = new StreamingContext(spark.sparkContext, Seconds(2))
    Logger.getLogger("org").setLevel(Level.ERROR)
    import spark.implicits._
    val stream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
      streamingContext, locationStrategy = PreferConsistent, consumerStrategy = Subscribe[String, String](Array("velib-stations"), kafkaParams))
    //reccuperer le message
    val message: DStream[String] = stream.map(x => x.value)
    message.foreachRDD(x => {
      val row_to_dataSet: Dataset[String] = x.toDS()
      val row_to_dataSet_parsed = row_to_dataSet.withColumn("ss", from_json(col("value"), schema_valid)).drop("value")
      row_to_dataSet_parsed
        .write
        .format("org.elasticsearch.spark.sql")
        .option("es.port", "9200")
        .option("es.nodes", "localhost")
        .mode("append")
        .save("/doc")
    })
    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
