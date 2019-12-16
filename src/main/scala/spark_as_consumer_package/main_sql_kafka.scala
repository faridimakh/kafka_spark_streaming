package spark_as_consumer_package

import common_tools.vals._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, from_json}
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

    val consoleOutput: DataFrame = inputDf
      .withColumn("value_toString", col("value")
        .cast("string"))

    val consoleOutput1 = consoleOutput.
      withColumn("value_toCols", from_json(col("value_toString"), shemavilib))
      //      .select("value_toCols.banking")
      .select("timestamp", "value_toCols")
    //      .where("value_toCols.contract_name is not null")

    val dd = consoleOutput1.writeStream
      .outputMode("append")
      .format("console").option("truncate", value = false)
      .start()
    dd.awaitTermination()
  }
}