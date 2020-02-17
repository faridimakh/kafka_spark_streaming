package kafka_to_elk

import scala.sys.process.Process

object producer_vilib_to_kafka {
  Process("python3 src/main/scala/kafka_to_elk/produce_stations.py").!
}
