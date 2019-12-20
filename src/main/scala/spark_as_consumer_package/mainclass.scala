package spark_as_consumer_package

import common_tools.vals._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object mainclass {
  def main(args: Array[String]): Unit = {

    spark.sparkContext.setLogLevel("WARN")
    val inputDf = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "velib-stations")
      .option("encoding", "UTF-8")
      .option("startingOffsets", "latest")
      .load()

    val message: DataFrame = inputDf
      .withColumn("value_toString", col("value")
        .cast("string"))
    //pour marseille par example
    val Latitude = 43.278932
    val Longitude = 5.3727702
    val message_parsed = message.

      //    --------------------------------------------------------------------------------------------------------------
      withColumn("value_toCols", from_json(col("value_toString"), schema_valid))
      .withColumn("my_lat", lit(Latitude)).withColumn("my_lang", lit(Longitude))
      .withColumn("rayon", sqrt(pow(col("value_toCols.position.lat") - col("my_lat"), 2) +
        pow(col("value_toCols.position.lng") - col("my_lang"), 2)))
      .select("timestamp", "value_toCols.address", "value_toCols.contract_name", "value_toCols.position", "my_lat", "my_lang", "rayon")
      .where(col("rayon") < 0.02)


    val Dsream_final = message_parsed.writeStream
      .outputMode("append")
      .format("console").option("truncate", value = false)
      .start()
    Dsream_final.awaitTermination()

  }
}