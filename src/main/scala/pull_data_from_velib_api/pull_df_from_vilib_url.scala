package pull_data_from_velib_api
import java.util.Calendar
import common_tools.functions._
import common_tools.vals._
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{current_timestamp, date_format, lit}

protected case class pull_df_from_vilib_url(time_listening: Int) {
  var df_velib_stations_receiver: DataFrame = spark.read json Get_Json_from_url(url)
  val actual: BigInt = actual_time_add_step(time_listening) //step in millisecond
  var i: Long = Calendar.getInstance().getTimeInMillis
  while (i < actual) {
    df_velib_stations_receiver = df_velib_stations_receiver.union(spark.read.json(Get_Json_from_url(url)))
    Thread.sleep(time_listening)
    i = Calendar.getInstance().getTimeInMillis
  }
  df_velib_stations_receiver.printSchema()
  df_velib_stations_receiver = df_velib_stations_receiver.withColumn("date", date_format(lit(current_timestamp()), "MM-dd-yyyy"))
    .withColumn("time", date_format(lit(current_timestamp()), "HH:mm:ss"))
  save_df(df_velib_stations_receiver, namedf = "vilibdata", format_saving = "json")
  println("------------------------------------------------------------------------------------------------------")
  println("------------------------------------------------------------------------------------------------------")
  println("your Data vilib for "+time_listening/1000+" Scondes are stored in the path:  [ "+ path_query_for_storage+" ]")
  println("------------------------------------------------------------------------------------------------------")
  println("------------------------------------------------------------------------------------------------------")
}
