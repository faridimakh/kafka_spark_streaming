import common_tools.vals._
import kafka_to_elk.get_from_kafka_process_and_push_to_elastic

import scala.sys.process.Process

object mainApp extends {
  def main(args: Array[String]): Unit = {
    val input = args(0) //for lanching a jar
    if (Set("produce", "process").contains(input)) {
      if (input == "produce") {
        println("producer from https://api.jcdecaux.com to kafka is lunched....")
        println("you ar sending data to  topic named: " + maTopic + "....")
        Process("python3 src/main/scala/kafka_to_elk/produce_stations.py").!
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


//for build project : sbt clean package