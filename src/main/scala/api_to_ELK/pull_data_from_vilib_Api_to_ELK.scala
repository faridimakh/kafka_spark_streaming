package api_to_ELK

import common_tools.functions._
import common_tools.vals.{spark, url}
import org.apache.spark.sql.functions.rand
import org.elasticsearch.spark.sql._

/**
 * @param waiting_time_before_asking_api :intervalle de temps entre l'appelle à l'api
 */
case class pull_data_from_vilib_Api_to_ELK(waiting_time_before_asking_api: Int = 1000) {
  while (true) {
    process_data_api(spark.read.json(Get_Json_from_url(url)))
    .withColumn("random_col", rand() * 3)
    .withColumn("random_col2", rand() * 3)
      .saveToEs("vilib/1", Map("es.mapping.id" -> "name"))
    Thread.sleep(waiting_time_before_asking_api)
  }
}