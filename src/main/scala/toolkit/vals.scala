package toolkit

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}
import toolkit.functions.{ConfigFormat_to_MapFormat, getArgsFomCaseClass}

import scala.collection.immutable
object vals {
  
  //-----------------------------------------------------------------------------------------------------------------------------------------------
  final lazy val myconf: Config = ConfigFactory.load("application.conf")

  final lazy val url = myconf.getString("url")
  final lazy val spark: SparkSession = new SparkSession.Builder().appName(myconf.getString("spark.name"))
    .master(myconf.getString("spark.master")).getOrCreate()
  final lazy val spark_elastic_config: Seq[(String, Object)] = ConfigFormat_to_MapFormat(myconf.getConfig("elsastic")).toList
  spark_elastic_config.foreach(x => spark.conf.set(x._1, x._2.toString))
  final lazy val kafkaParams: Map[String, Object] = ConfigFormat_to_MapFormat(myconf.getConfig("kafkaParamsConsum"))

  //-----------------------------------------------------------------------------------------------------------------------------------------------
  final lazy val streamingContext: StreamingContext = new StreamingContext(spark.sparkContext, Seconds(1))
  //-----------------------------------------------------------------------------------------------------------------------------------------------

  final lazy val ma_Topic = "vilib_station"
  final lazy val indexNameElasticsearch = "vilib"
  final lazy val coloumn_vilib_api: immutable.Seq[String] = getArgsFomCaseClass[station]
  //-----------------------------------------------------------------------------------------------------------------------------------------------

}

