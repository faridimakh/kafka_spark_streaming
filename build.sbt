name := "kafka_spark_streaming"

version := "0.1"

scalaVersion := "2.11.11"

val spark_Version = "2.2.2"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % spark_Version,
  "org.apache.spark" %% "spark-sql" % spark_Version,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % spark_Version,
  "org.apache.spark" % "spark-streaming_2.11" % spark_Version,
  "org.elasticsearch" %% "elasticsearch-spark-20" % "7.5.0",
//  "org.elasticsearch" %% "elasticsearch-spark-20" % "6.0.0",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.0.0",
  "org.scala-lang" % "scala-library" % scalaVersion.value

)
