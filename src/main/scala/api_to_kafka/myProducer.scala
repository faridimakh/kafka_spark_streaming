package api_to_kafka

import org.apache.kafka.clients.producer.ProducerRecord
 case class message_kafka(topic: String, value: String)
protected object myProducer {
  /**
   *
   * @param m : couple (topic name, message to send)
   */
  def push_message(m: message_kafka): Unit = {
    val record: ProducerRecord[String, String] = new ProducerRecord(m.topic, m.value)
    producer_tune_parms.get_producer_tuned.send(record)
  }
}
