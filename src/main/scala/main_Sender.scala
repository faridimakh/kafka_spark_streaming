import common_tools.vals.spark
import kafka_to_elk.producer_vilib_to_kafka

object main_Sender {
  def main(args: Array[String]): Unit = {
    spark.sparkContext.setLogLevel("WARN")
    //    val input = "produce" //arg(0) for lanching a jar
    //    if (Set("produce", "process").contains(input)) {
    //      if (input == "produce") {
    //        println("producer from https://api.jcdecaux.com to kafka is lunched....")
    //        println("you ar sending data to  topic named: " + maTopic + "....")
    //        producer_vilib_to_kafka
    //      }
    //      else
    //        println("procucer from Api vilib_statyion is lunched....")
    //      get_from_kafka_process_and_push_to_elastic
    //    }
    //    else {
    //      println("your arg parameter must be in: {produce, process}")
    //      println("change parameter with previous values please!")
    //    }
    producer_vilib_to_kafka
  }
}
