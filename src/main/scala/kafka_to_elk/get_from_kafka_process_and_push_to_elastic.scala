package kafka_to_elk

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Dataset, Encoder}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.elasticsearch.spark.sql._
import toolkit.functions._
import toolkit.station
import toolkit.vals._

object get_from_kafka_process_and_push_to_elastic extends Thread {
  override def run(): Unit = {
    println("processing and pushing data to elasticsearch is lanched....")
    println("index "+indexNameElasticsearch+" is created in elasticsearch....")
    Logger.getLogger("org").setLevel(Level.ERROR)

    import spark.implicits._

    val stream: InputDStream[ConsumerRecord[String, String]] =
      KafkaUtils.createDirectStream[String, String](
        streamingContext, locationStrategy = PreferConsistent, consumerStrategy = Subscribe[String, String](Array(ma_Topic), kafkaParams))
    //reccuperer le message
    val message: DStream[String] = stream.map(x => x.value)
    message.foreachRDD(x => {
      val row_to_dataSet: Dataset[String] = x.toDS()
      var row_to_dataSet_parsed: DataFrame = row_to_dataSet
        .withColumn("structuredColumn", from_json(col("value"), implicitly[Encoder[station]].schema)).drop("value")
      coloumn_vilib_api.foreach(x => row_to_dataSet_parsed = row_to_dataSet_parsed.withColumn(x, col("structuredColumn." + x)))
      row_to_dataSet_parsed = row_to_dataSet_parsed.drop("structuredColumn")
      row_to_dataSet_parsed = process_data_api(row_to_dataSet_parsed)

      row_to_dataSet_parsed.saveToEs(indexNameElasticsearch.concat("/1"), Map("es.mapping.id" -> "name"))
    })
    streamingContext.start()
    streamingContext.awaitTermination()
  }

}