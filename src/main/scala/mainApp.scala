import common_tools.vals._
import kafka_to_elk.get_from_kafka_process_and_push_to_elastic

import scala.sys.process.Process

object mainApp  {
  def main(args: Array[String]): Unit = {
    val input ="produce" //args(0) //for lanching a jar
    if (Set("produce", "process").contains(input)) {
      if (input == "produce") {
        println("producer from https://api.jcdecaux.com to kafka is lunched....")
        println("you are sending data to  topic name: " + ma_Topic + "....")
        Process("python3 src/main/scala/kafka_to_elk/produce_stations.py " + ma_Topic).!
      }
      else
        println("procucer from Api vilib_statyion is lunched....")
      get_from_kafka_process_and_push_to_elastic
    }
    else {
      println("your arg parameter must be in: {produce, process}")
      println("change parameter with previous values please!")
    }
    get_from_kafka_process_and_push_to_elastic
  }

}


