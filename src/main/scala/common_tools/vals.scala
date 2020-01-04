package common_tools

import java.util.Calendar

import org.apache.spark.sql
import org.apache.spark.sql.types._

object vals {
  final val spark = new sql.SparkSession.Builder()
    .appName("velib_app")
    .master("local[*]") //change to "yarn" for cluster running
    .getOrCreate()
  final val spark_elastic_config = Seq(("es.nodes", "localhost"), ("es.port", "9200"), ("es.index.auto.create", "true"),
    ("spark.serializer", "org.apache.spark.serializer.KryoSerializer"), ("es.write.operation", "upsert"))
  spark_elastic_config.foreach(x => spark.conf.set(x._1, x._2))

  final lazy val actual_time_add_listening_time: BigInt => BigInt = (listening_time: BigInt) => Calendar.getInstance().getTimeInMillis + listening_time
  final lazy val url = "https://api.jcdecaux.com/vls/v1/stations?apiKey=2a5d13ea313bf8dc325f8783f888de4eb96a8c14"
  final lazy val path_data_storage = "/home/farid/Bureau/vlibStation/"

  private val position_shema: StructType = new StructType()
    .add("lat", DoubleType)
    .add("lng", DoubleType)

  val schema_valid: StructType = new StructType()
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

