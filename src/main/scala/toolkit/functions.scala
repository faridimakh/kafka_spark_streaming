package toolkit

import com.typesafe.config.Config
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._

import scala.collection.immutable
import scala.reflect.runtime.universe._

trait functions {
  //-----------------------------------------------------------------------------------------------------------------------------------------------
  def process_data_api(df: DataFrame): DataFrame = {
    val df1 = df
      .withColumn("lat", col("position.lat"))
      .withColumn("lng", col("position.lng"))
      .withColumn("timestamp", lit(current_timestamp()))
      .withColumn("random_col", when(rand() > 0.5, when(rand() > 0.2, 3).otherwise(2)).otherwise(0))
      .withColumn("random_col", rand() * 3)
      .withColumn("random_col2", rand() * 3)
    df1
  }

  //-----------------------------------------------------------------------------------------------------------------------------------------------
  def ConfigFormat_to_MapFormat(config: Config): Map[String, Object] = {
    import scala.collection.JavaConversions._
    val map: Map[String, Object] = config.entrySet().map({ entry =>
      entry.getKey -> entry.getValue.unwrapped()
    })(collection.breakOut)
    map
  }

  //-----------------------------------------------------------------------------------------------------------------------------------------------
  def getArgsFromCaseClass[T: TypeTag]: immutable.Seq[String] = typeOf[T].members.collect {
    case m: MethodSymbol if m.isCaseAccessor => m
  }.toList.map(x => x.toString.replaceAll("value", "").trim)

  def getArgsFromCaseClassbis[T: TypeTag]: List[String] = {
    typeOf[T].members.map(x => x.toString)
      .filter(_.contains("value"))
      .map(x => x.replace("value", "").trim)
      .toSet
      .toList
  }

}
