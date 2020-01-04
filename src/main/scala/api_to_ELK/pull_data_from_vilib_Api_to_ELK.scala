package api_to_ELK

import common_tools.functions.{Get_Json_from_url, process_data_api}
import common_tools.vals.{spark, url}
import org.elasticsearch.spark.sql._

/**
 *
 * @param time_listening                 : le temps que vous souhaiter  pour rcouter l'api
 * @param waiting_time_before_asking_api : intervalle de temps entre l'appelle à l'api
 */
case class pull_data_from_vilib_Api_to_ELK(time_listening: Int, waiting_time_before_asking_api: Int = 1000) {
  if (this.time_listening < waiting_time_before_asking_api)
    println("time_listening= " + time_listening + " Error, ,must bye must be higher than waiting_time_before_asking_api= " + waiting_time_before_asking_api + " ! chang values!")
  else {
    while (true) {
      process_data_api(spark.read.json(Get_Json_from_url(url))).saveToEs("testtt/1", Map("es.mapping.id" -> "name"))
      Thread.sleep(waiting_time_before_asking_api)
    }

  }


}