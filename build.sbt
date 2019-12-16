name := "kafka_spark_streaming"

version := "0.1"

scalaVersion := "2.11.12"

val spark_Version = "2.2.2"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % scalaVersion.value,
  "org.apache.spark" %% "spark-sql" % spark_Version,
  "org.apache.spark" %% "spark-sql-kafka-0-10" % spark_Version
)