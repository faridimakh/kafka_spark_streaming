package api_to_kafka
import java.util.Calendar

import common_tools.vals._
import common_tools.functions._
protected object main_Sender {
  def main(args: Array[String]): Unit = {
//    ecouter l'api et envoyer vers le topic velib-stations pour 6 seconds
//    val actual: BigInt = actual_time_add_step(6000) //step in millisecond
//    var i: Long = Calendar.getInstance().getTimeInMillis
//    while (i <= actual) {
//      spark.read.json(Get_Json_from_url(url)).rdd.foreach(x => myProducer.push_message(message_kafka("velib-stations", x.toString)))
//      Thread.sleep(1000)
//      i = Calendar.getInstance().getTimeInMillis
//    }

//    ecouter l'api et envoyer vers le topic velib-stations indifinement dans le temps (d'une façon permanant)
    var i: Long = Calendar.getInstance().getTimeInMillis
    while (true) {
      spark.read.json(Get_Json_from_url(url)).rdd.foreach(x => myProducer.push_message(message_kafka("velib-stations", x.toString)))
//      Thread.sleep(1000)
      i = Calendar.getInstance().getTimeInMillis
    }
  }
}