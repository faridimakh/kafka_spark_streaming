package producetokafka

import common_tools.functions.Get_Json_from_url
import common_tools.vals.{maTopic, producer_tuned, url}
import org.apache.kafka.clients.producer.ProducerRecord

object pushDataToKafka {
  while (true) {
    Get_Json_from_url(url).foreach(x => producer_tuned.send(new ProducerRecord(maTopic, x)))
    Thread.sleep(1)

  }
}