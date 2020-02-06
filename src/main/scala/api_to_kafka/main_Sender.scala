package api_to_kafka
import common_tools.functions._
import common_tools.vals._
protected object main_Sender {
  def main(args: Array[String]): Unit = {
    while (true) {
      process_data_api(spark.read.json(Get_Json_from_url(url))).rdd.foreach(x => myProducer.push_message(message_kafka("velib-stations", x.toString)))
      Thread.sleep(1000)
    }
  }
}


//
//package packfar
//import java.util.Properties
//import com.typesafe.config.{Config, ConfigFactory}
//
//object conftest {
//  def propsFromConfig(config: Config): Properties = {
//    import scala.collection.JavaConversions._
//    val props = new Properties()
//    val map: Map[String, Object] = config.entrySet().map({ entry =>
//      entry.getKey -> entry.getValue.unwrapped()
//    })(collection.breakOut)
//    props.putAll(map)
//    props
//  }
//
//  def main(args: Array[String]): Unit = {
//    val topicConfig: Config = ConfigFactory.load("maco.conf").getConfig("comg")
//    val props: Properties = propsFromConfig(topicConfig)
//    println(props.get("key.serializer"))
//  }
//
//  /*config should contain this:
//  comf {
//    acks = "all"
//    retries = new Integer(1)
//    batch.size = new Integer(1)
//    linger.ms = new Integer(1)
//    buffer.memory = new Integer(255587878)
//  }
//  comg {
//    bootstrap.servers = "localhost:9092"
//    key.serializer = "org.apache.kafka.common.serialization.StringSerializer"
//    value.serializer = "org.apache.kafka.common.serialization.StringSerializer"
//    client.id = "SampleProducer"
//  }
//*/
//}
