package api_to_ELK

import java.util.Calendar

import common_tools.functions.Get_Json_from_url
import common_tools.vals.{actual_time_add_listening_time, spark, url}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{current_timestamp, date_format, lit}

/**
 *
 * @param time_listening                 : le temps que vous souhaiter  pour rcouter l'api
 * @param waiting_time_before_asking_api : intervalle de temps entre l'appelle à l'api
 */
case class pull_data_from_vilib_Api_to_ELK(time_listening: Int, waiting_time_before_asking_api: Int = 1000) {
  if (this.time_listening < waiting_time_before_asking_api)
    println("time_listening= " + time_listening + " Error, ,must bye must be higher than waiting_time_before_asking_api= " + waiting_time_before_asking_api + " ! chang values!")
  else {
    var df_velib_stations_receiver: DataFrame = spark.read json Get_Json_from_url(url)
    val actual: BigInt = actual_time_add_listening_time(time_listening)

    var i: Long = Calendar.getInstance().getTimeInMillis
    while (i < actual) {
      df_velib_stations_receiver = df_velib_stations_receiver.union(spark.read.json(Get_Json_from_url(url)))
      Thread.sleep(waiting_time_before_asking_api)
      i = Calendar.getInstance().getTimeInMillis
    }
    df_velib_stations_receiver.printSchema()
    df_velib_stations_receiver = df_velib_stations_receiver.withColumn("date", date_format(lit(current_timestamp()), "MM-dd-yyyy"))
      .withColumn("time", date_format(lit(current_timestamp()), "HH:mm:ss"))
    df_velib_stations_receiver.write
      .format("org.elasticsearch.spark.sql")
      .option("es.port", "9200")
      .option("es.nodes", "localhost")
      .mode("append")
//      .save("vilimb/doc")
      .save("vilimbfar/doc")
  }
}