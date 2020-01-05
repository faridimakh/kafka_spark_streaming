package api_to_local

import java.util.Calendar

import common_tools.functions._
import common_tools.vals._
import org.apache.spark.sql.DataFrame

/**
 *
 * @param time_listening                 : le temps que vous souhaiter  pour ecouter l'api
 * @param waiting_time_before_asking_api : intervalle de temps entre l'appelle à l'api
 */
protected case class pull_data_from_vilib_Api_to_local(time_listening: Int=10000, waiting_time_before_asking_api: Int = 1000) {
  if (this.time_listening < waiting_time_before_asking_api)
    println("time_listening= " + time_listening + " Error, ,must bye must be higher than waiting_time_before_asking_api= " + waiting_time_before_asking_api + " ! chang values!")
  else {
    var df_velib_stations_receiver: DataFrame = process_data_api(spark.read json Get_Json_from_url(url))
    val actual: BigInt = actual_time_add_listening_time(time_listening) //step in millisecond
    while (Calendar.getInstance().getTimeInMillis < actual) {
      df_velib_stations_receiver = df_velib_stations_receiver.union(process_data_api(spark.read.json(Get_Json_from_url(url))))
      Thread.sleep(waiting_time_before_asking_api)
    }
    df_velib_stations_receiver.printSchema()
    df_velib_stations_receiver.show(20, truncate = false)
    save_df(df_velib_stations_receiver, name_saving_df = "vilibdata", format_saving_df = "json")
      lazy val sssmm=df_velib_stations_receiver.schema
    println("------------------------------------------------------------------------------------------------------")
    println("------------------------------------------------------------------------------------------------------")
    println("your Data vilib for " + time_listening / 1000 + " Scondes are stored in the path:  [ " + path_data_storage + " ]")
    println("------------------------------------------------------------------------------------------------------")
    println("------------------------------------------------------------------------------------------------------")
  }
}
