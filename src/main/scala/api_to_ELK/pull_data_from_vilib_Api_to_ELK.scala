package api_to_ELK

import common_tools.functions.{Get_Json_from_url, process_data_api}
import common_tools.vals.{spark, url}
import org.elasticsearch.spark.sql._

/**
 *
 * @param waiting_time_before_asking_api : intervalle de temps entre l'appelle à l'api
 */
case class pull_data_from_vilib_Api_to_ELK(waiting_time_before_asking_api: Int = 1000) {
  while (true) {
    process_data_api(spark.read.json(Get_Json_from_url(url))).saveToEs("vilib/1", Map("es.mapping.id" -> "name"))
    Thread.sleep(waiting_time_before_asking_api)
  }
}