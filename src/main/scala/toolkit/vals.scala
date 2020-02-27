package toolkit

import java.util.Calendar

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import toolkit.functions.ConfigFormat_to_MapFormat

object vals {
  final lazy val spark_elastic_config: Seq[(String, Object)] = ConfigFormat_to_MapFormat(myconf.getConfig("elsastic")).toList
  final lazy val url = myconf.getString("url")
  final val myconf: Config = ConfigFactory.load("application.conf")
  //  final val spark = myconf.getString("spark")

  final lazy val spark: SparkSession = new SparkSession.Builder().appName(myconf.getString("spark.name"))
    .master(myconf.getString("spark.master")).getOrCreate()
  spark_elastic_config.foreach(x => spark.conf.set(x._1, x._2.toString))


  final lazy val actual_time_add_listening_time: BigInt => BigInt = (listening_time: BigInt) => Calendar.getInstance().getTimeInMillis + listening_time
  final lazy val kafkaParams: Map[String, Object] = ConfigFormat_to_MapFormat(myconf.getConfig("kafkaParamsConsum"))
  final lazy val streamingContext: StreamingContext = new StreamingContext(spark.sparkContext, Seconds(1))

//  final lazy val ma_Topic = "vilib_station_vilib_station"
  final lazy val ma_Topic = "vilib_farrrrrrrrrrrrr"
  final lazy val coloumn_vilib_api = List("number", "contract_name", "name", "address", "position", "banking", "bonus", "bike_stands", "available_bike_stands", "available_bikes", "status", "last_update")
  private val position_shema: StructType = new StructType()
    .add("lat", DoubleType)
    .add("lng", DoubleType)

  final lazy val schema_vilib_data: StructType = new StructType()
    .add("number", IntegerType, nullable = true)
    .add("contract_name", StringType, nullable = true)
    .add("name", StringType, nullable = true)
    .add("address", StringType, nullable = true)
    .add("position", position_shema, nullable = true)
    .add("banking", BooleanType, nullable = true)
    .add("bonus", BooleanType, nullable = true)
    .add("bike_stands", IntegerType, nullable = true)
    .add("available_bike_stands", IntegerType, nullable = true)
    .add("available_bikes", IntegerType, nullable = true)
    .add("status", StringType, nullable = true)
    .add("last_update", LongType, nullable = true)
}

