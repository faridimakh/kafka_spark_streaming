package api_to_kafka
import common_tools.functions._
import common_tools.vals._
protected object main_Sender {
  def main(args: Array[String]): Unit = {
    while (true) {
      process_data_api(spark.read.json(Get_Json_from_url(url))).rdd.foreach(x => myProducer.push_message(message_kafka("velib-stations", x.toString)))
      Thread.sleep(1000)
    }
  }
}