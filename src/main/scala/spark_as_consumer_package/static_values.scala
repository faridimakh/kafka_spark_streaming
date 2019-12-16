package spark_as_consumer_package

import org.apache.spark.sql.types._

object static_values {

 val psition_shema: StructType = new StructType()
   .add("lat", DoubleType)
   .add("lng", DoubleType)

  val shemavilib: StructType = new StructType()
   .add("number", IntegerType, nullable = true)
   .add("contract_name", StringType, nullable = true)
   .add("name", StringType, nullable = true)
   .add("address", StringType, nullable = true)
   .add("position", psition_shema, nullable = true)
   .add("banking", BooleanType, nullable = true)
   .add("bonus", BooleanType, nullable = true)
   .add("bike_stands", IntegerType, nullable = true)
   .add("available_bike_stands", IntegerType, nullable = true)
   .add("available_bikes", IntegerType, nullable = true)
   .add("status", StringType, nullable = true)
   .add("last_update", LongType, nullable = true)
}
