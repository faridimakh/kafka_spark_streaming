package common_tools

import common_tools.vals._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

object functions {
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
      .withColumn("random_col", when(rand() > 0.5, when(rand() > 0.2, 3).otherwise(2)).otherwise(0))
      //pour voir un changement remarquable de données j'ai rajouté (vélos) j'ai rajouté deux colume qui change continuellement
      .withColumn("random_col", rand() * 3)
      .withColumn("random_col2", rand() * 3)
    df1

  }
}
