package api_to_local
import org.apache.spark.{SparkConf, SparkContext}
import org.elasticsearch.spark.sql._

object test_remove_after extends App {
  //    spark.sparkContext.setLogLevel("WARN")
  //   lazy val a=spark.read json Get_Json_from_url(url)
  //    var mamas=scala.collection.mutable.Map(
  ////        "id1"->List("farid1","dd1"),
  ////        "id2"->List("farid2","dd2"),
  ////        "id3"->List("farid3","dd3")
  //        "id1"->"farid1",
  //        "id2"->"farid2",
  //        "id3"->"farid3"
  //    )
  //    val dfmamap=mamas.toSeq.toDF("num","nom")
  //    dfmamap.write.format("org.elasticsearch.spark.sql")
  //      .option("es.port", "9200")
  //      .option("es.nodes", "localhost")
  //      .mode("append")
  //      .save("testfar/doc")
  val sparkConf = new SparkConf().setAppName("SparkEs1").setMaster("local")
  sparkConf.set("es.nodes", "localhost")
  sparkConf.set("es.port", "9200")
  sparkConf.set("es.index.auto.create", "true")
  sparkConf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
  sparkConf.set("es.write.operation", "upsert")

  val sc = new SparkContext(sparkConf)

  import org.apache.spark.sql._

  val sqlContext = new SQLContext(sc)

  case class A(a: String, b: Option[String], c: Option[String])

//  val a = A("a1", Some("b1"), None)
//  val rdd = sc.makeRDD(Seq(a))
//  val df = sqlContext.createDataFrame(rdd)
//  df.show()
//  df.saveToEs("test/1", Map("es.mapping.id" -> "a"))
//
//  val a1=A("a2",None,Some("c1"))
//  val rdd1 = sc.makeRDD(Seq(a1))
//  val df1 = sqlContext.createDataFrame(rdd1)
//  df1.saveToEs("test/1",Map("es.mapping.id" -> "a"))

  val a2=A("a222",None,Some("c8888"))
  val rdd2 = sc.makeRDD(Seq(a2))
  val df2 = sqlContext.createDataFrame(rdd2)
  df2.saveToEs("test/1",Map("es.mapping.id" -> "a"))

}
