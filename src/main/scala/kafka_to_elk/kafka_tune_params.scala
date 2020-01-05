package kafka_to_elk

import org.apache.kafka.common.serialization.StringDeserializer

object kafka_tune_params {
   val DStream_reader_Params = Seq(("kafka.bootstrap.servers", "localhost:9092"), ("subscribe", "velib-stations"), ("encoding", "UTF-8"), ("startingOffsets", "latest"))

  val kafkaParams: Map[String, Object] = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "auto.offset.reset" -> "latest",
    "group.id" -> "test-consumer-group" //le groupe de consumer par default
  )
}
