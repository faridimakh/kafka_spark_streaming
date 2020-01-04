package common_tools

import common_tools.vals._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object functions {
  /**
   * @param save_this_df     : dataframe name to store
   * @param nb_partition     : nonber of partition that you subdivide your df
   * @param format_saving_df : storage, default is 'csv' , you can channge to parket, json...
   * @param path_saving_df   : where you want store your df
   * @param name_saving_df   : name df to store
   */
  def save_df(save_this_df: DataFrame, nb_partition: Int = 1,
              path_saving_df: String = path_data_storage,
              name_saving_df: String,
              format_saving_df: String = "com.databricks.spark.csv",
              mode_saving_df: String = "Overwrite"): Unit = {
    save_this_df.coalesce(nb_partition).write.mode(mode_saving_df).format(format_saving_df).option("header", "true")
      .save(path_saving_df + name_saving_df)
  }

  def Get_Json_from_url(url: String): RDD[String] = {
    val result = scala.io.Source.fromURL(url).mkString
    val jsonResponseOneLine = result.toString.stripLineEnd
    val jsonRdd = spark.sparkContext.parallelize(jsonResponseOneLine :: Nil)
    jsonRdd
  }

  def process_data_api(df: DataFrame): DataFrame = {
    val df1 = df
      .withColumn("lat", col("position.lat"))
      .withColumn("lng", col("position.lng"))
      .withColumn("timestamp", lit(current_timestamp()))
      .withColumn("isVal", when(rand() > 0.5,when(rand()>0.2,3).otherwise(2)).otherwise(0))
    //      .drop("position")
    df1

  }
}
