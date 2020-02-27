package kafka_to_elk

import toolkit.vals.{ma_Topic, myconf, url}

import scala.sys.process.Process

object pruducer extends Thread {
  override def run(): Unit = {
    println("producer from https://api.jcdecaux.com to kafka is lunched....")
    println("you are sending data to  topic name: " + ma_Topic + "....")
    Process("python3 src/main/scala/kafka_to_elk/produce_stations.py " + ma_Topic+" "+url+" "+myconf.getString("kafkaParamsConsum.bootstrap.servers")).!
  }

}
