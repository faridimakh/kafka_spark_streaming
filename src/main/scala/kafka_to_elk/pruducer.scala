package kafka_to_elk

import toolkit.statictools

import scala.sys.process.Process

object pruducer extends Thread with statictools {
  override def run(): Unit = {
    println("producer from https://api.jcdecaux.com to kafka is lunched....")
    println("you are sending data to  topic name: " + TopicName + "....")
    Process("python3 src/main/scala/kafka_to_elk/produce_stations.py " + TopicName + " " + url + " " + myconf.getString("kafkaParamsConsum.bootstrap.servers")).!
  }

}
