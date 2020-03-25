package toolkit

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.immutable

trait statictools extends functions {

  //-----------------------------------------------------------------------------------------------------------------------------------------------
  final lazy val myconf: Config = ConfigFactory.load("application.conf")

  final lazy val url = myconf.getString("url")
  final lazy val spark: SparkSession = new SparkSession.Builder().appName(myconf.getString("spark.name")).master(myconf.getString("spark.master")).getOrCreate()
  final lazy val spark_elastic_config: Seq[(String, Object)] = ConfigFormat_to_MapFormat(myconf.getConfig("elsastic")).toList
  spark_elastic_config.foreach(x => spark.conf.set(x._1, x._2.toString))
  final lazy val kafkaParams: Map[String, Object] = ConfigFormat_to_MapFormat(myconf.getConfig("kafkaParamsConsum"))

  //-----------------------------------------------------------------------------------------------------------------------------------------------
  final lazy val streamingContext: StreamingContext = new StreamingContext(spark.sparkContext, Seconds(1))
  //-----------------------------------------------------------------------------------------------------------------------------------------------

  final lazy val TopicName = "vilib_station"
  final lazy val Elasticsearch_indexName = "index_vilib"
  final lazy val coloumn_vilib_api: immutable.Seq[String] = getArgsFromCaseClass[station]

  //-----------------------------------------------------------------------------------------------------------------------------------------------

  case class position_station private(lat: Double, lng: Double) {
    def getJson: String = s"{'lat':$lat,'lng':$lng}".replaceAll("'", "\"")
  }

  case class station(number: Int,
                     contract_name: String,
                     name: String,
                     address: String,
                     position: position_station,
                     banking: Boolean,
                     bonus: Boolean,
                     bike_stands: Int,
                     available_bike_stands: Int,
                     available_bikes: Int,
                     status: String,
                     last_update: Long) {
    def getJson: String = s"{'number':$number,'contract_name':'$contract_name','name':'$name','address':'$address','position':${position.getJson},'banking':$banking,'bonus':$bonus,'bike_stands':$bike_stands,'available_bike_stands':$available_bike_stands,'available_bikes':$available_bikes,'status':'$status','last_update':$last_update}".replaceAll("'", "\"")
  }

}