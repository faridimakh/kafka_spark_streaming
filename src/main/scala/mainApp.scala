import kafka_to_elk.{get_from_kafka_process_and_push_to_elastic, pruducer}

object mainApp  {
  def main(args: Array[String]): Unit = {
    get_from_kafka_process_and_push_to_elastic.run()
    pruducer.run()
  }

}


