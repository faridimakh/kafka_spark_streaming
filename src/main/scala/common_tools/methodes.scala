package common_tools

import common_tools.vals._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SaveMode}

object methodes {
  /**
   * @param df              : dataframe name to store
   * @param nb_partition    : nonber of partition that you subdivide your df
   * @param format_saving   : storage, default is 'csv' , you can channge to parket, json...
   * @param path_storage_df : where you want store your df
   * @param namedf          : name df to store
   */
  def save_df(df: DataFrame, nb_partition: Int = 1, format_saving: String = "com.databricks.spark.csv", path_storage_df: String = path_query_for_storage, namedf: String): Unit = {
    df.coalesce(nb_partition).write.mode(SaveMode.Overwrite).format(format_saving).option("header", "true")
      .save(path_storage_df + namedf)
  }

  def Get_Json_from_url(url: String): RDD[String] = {
    val result = scala.io.Source.fromURL(url).mkString
    val jsonResponseOneLine = result.toString.stripLineEnd
    val jsonRdd = spark.sparkContext.parallelize(jsonResponseOneLine :: Nil)
    jsonRdd
  }
}
