package kafka_to_elk

import scala.sys.process.Process

object producer_vilib_to_kafka {
  Process("python3 src/main/resources/produce_stations.py").!
}
