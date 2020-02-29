import kafka_to_elk.{get_from_kafka_process_and_push_to_elastic, pruducer}

object mainApp  {


  def main(args: Array[String]): Unit = {
    get_from_kafka_process_and_push_to_elastic.start()
    pruducer.start()
//val x=station(2,"fff","rtrt","dfdf",position_station(5.3,5.2),banking = true,bonus = true,5,7,7,"sss",455)
//    println(x.getJson.asInstanceOf[Array[Byte]])
  }
}


