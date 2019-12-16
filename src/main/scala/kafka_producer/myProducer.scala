package kafka_producer

import org.apache.kafka.clients.producer.ProducerRecord

protected object myProducer {
  /**
   *
   * @param m: couple (topic name, message to send)
   */
  def push_message(m: message_kafka): Unit = {
    val record: ProducerRecord[String, String] = new ProducerRecord(m.topic, m.value)
    producer_instance.get_producer_tuned.send(record)
  }
}
