name := "kafka_spark_streaming"

version := "0.1"

scalaVersion := "2.11.8"

val spark_Version = "2.2.2"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % spark_Version,
  "org.apache.spark" %% "spark-sql" % spark_Version,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % spark_Version,
  "org.apache.spark" % "spark-streaming_2.11" % spark_Version,
  "org.scala-lang" % "scala-library" % scalaVersion.value
)


//scalaVersion := "2.11.8"
//val spark_version = "2.1.0"
//libraryDependencies ++= Seq(
//  "org.apache.spark" % "spark-core_2.11" % spark_version,
//  "org.apache.spark" % "spark-sql_2.11" % spark_version,
//  "org.apache.spark" % "spark-streaming_2.11" % spark_version)
//
//// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-8
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.0.0-preview"
