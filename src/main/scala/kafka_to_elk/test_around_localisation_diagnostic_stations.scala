package kafka_to_elk

import common_tools.vals.{schema_vilib_data, spark}
import kafka_to_elk.kafka_tune_params._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.DataStreamReader

object test_around_localisation_diagnostic_stations {
  def main(args: Array[String]): Unit = {

    val DataStreamReader_Options: DataStreamReader = spark.readStream.format("kafka")
    DStream_reader_Params.foreach(x => DataStreamReader_Options.option(x._1, x._2))
    val imputeDF = DataStreamReader_Options.load()
    val message: DataFrame = imputeDF
      .withColumn("value_toString", col("value")
        .cast("string"))

    //pour marseille par example:
    val Latitude = 43.278932
    val Longitude = 5.3727702

    //    --------------------------------------------------------------------------------------------------------------
    val message_parsed = message
      .withColumn("value_toCols", from_json(col("value_toString"), schema_vilib_data))
      .withColumn("my_lat", lit(Latitude)).withColumn("my_lang", lit(Longitude))
      .withColumn("rayon", sqrt(pow(col("value_toCols.position.lat") - col("my_lat"), 2) +
        pow(col("value_toCols.position.lng") - col("my_lang"), 2)))
      .select("timestamp", "value_toCols.address", "value_toCols.contract_name", "value_toCols.position", "my_lat", "my_lang", "rayon")
      .where(col("rayon") < 0.02)

    //    --------------------------------------------------------------------------------------------------------------
    val Dsream_final = message_parsed.writeStream
      .outputMode("append")
      .format("console")
      .option("truncate", value = false)
      .start()
    Dsream_final.awaitTermination()
  }
}
