package api_to_ELK
import common_tools.vals._
object mainclass {
  def main(args: Array[String]): Unit = {
    spark.sparkContext.setLogLevel("WARN")
    pull_data_from_vilib_Api_to_ELK(500)
  }

}
