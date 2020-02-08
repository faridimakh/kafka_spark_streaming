package api_to_kafka

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer

protected object producer_tune_parms {
  final val get_producer_tuned: KafkaProducer[String, String] = {

    val producerProps = new Properties()
    producerProps.put("bootstrap.servers", "localhost:9092")
    producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    producerProps.put("client.id", "SampleProducer")
    producerProps.put("acks", "all")
    producerProps.put("retries", new Integer(1))
    producerProps.put("batch.size", new Integer(1))
    producerProps.put("linger.ms", new Integer(1))
    producerProps.put("buffer.memory", new Integer(255587878))

    val producer = new KafkaProducer[String, String](producerProps)
    producer
  }
}
