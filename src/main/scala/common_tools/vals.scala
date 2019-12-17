package common_tools

import java.util.Calendar

import org.apache.spark.sql

object vals {

  final val spark = new sql.SparkSession.Builder()
    .appName("velib_app")
    .master("local[*]")
    .getOrCreate()
  final lazy val actual_time_add_step: BigInt => BigInt = (step: BigInt) => Calendar.getInstance().getTimeInMillis + step
  final lazy val url = "https://api.jcdecaux.com/vls/v1/stations?apiKey=2a5d13ea313bf8dc325f8783f888de4eb96a8c14"
  final lazy val path_query_for_storage = "/home/farid/Bureau/vlibStation/"
}

