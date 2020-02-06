package api_to_kafka

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer

protected object producer_tune_parms {
  final val get_producer_tuned: KafkaProducer[String, String] = {
    val producerProps = new Properties()
    producerProps.load(scala.io.Source.fromURL(getClass.getResource("producer.properties")).bufferedReader())
    val producer = new KafkaProducer[String, String](producerProps)
    producer
  }
}