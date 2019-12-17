package spark_as_consumer_package

import common_tools.vals._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import spark_as_consumer_package.static_values._

object main_sql_kafka {
  def main(args: Array[String]): Unit = {

    spark.sparkContext.setLogLevel("WARN")
    val inputDf = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "velib-stations")
      .option("startingOffsets", "latest")
      .option("encoding", "UTF-8")
      //      .option("startingOffsets", "earliest")
      .load()

    val message: DataFrame = inputDf
      .withColumn("value_toString", col("value")
        .cast("string"))
//pour marseille par example
    val Latitude = 43.278932
    val Longitude = 5.3727702
    val message_parsed = message.

      //    --------------------------------------------------------------------------------------------------------------
      withColumn("value_toCols", from_json(col("value_toString"), shemavilib))
      .withColumn("mylat", lit(Latitude)).withColumn("mylang", lit(Longitude))
      .withColumn("rayon", sqrt(pow(col("value_toCols.position.lat") - col("mylat"), 2) +
        pow(col("value_toCols.position.lng") - col("mylang"), 2)))
      .select("timestamp", "value_toCols.address", "value_toCols.contract_name", "value_toCols.position", "mylat", "mylang", "rayon")
      .where(col("rayon") < 0.02)


    val dd = message_parsed.writeStream
      .outputMode("append")
      .format("console").option("truncate", value = false)
      .start()
    dd.awaitTermination()
  }
}
