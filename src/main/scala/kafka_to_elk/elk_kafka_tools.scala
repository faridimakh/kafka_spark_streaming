package kafka_to_elk

import org.apache.kafka.common.serialization.StringDeserializer

object elk_kafka_tools {
  val kafkaParams: Map[String, Object] = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "auto.offset.reset" -> "latest",
    "group.id" -> "test-consumer-group" //le groupe de consumer par defaut
  )
}
