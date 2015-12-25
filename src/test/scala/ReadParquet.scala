import org.apache.hadoop.fs.FileSystem
import org.apache.spark.sql.{SaveMode, SQLContext}
import org.apache.spark.{SparkContext, SparkConf}

import scala.util.parsing.json.JSON

/**
 * Created by root on 15-10-29.
 */
object ReadParquet {
  def main(args: Array[String]) {
//    if (args.length < 3) {
//      System.err.println("Usage: " +
//        "| CrushFile <dfsUri> <abFromDir> <abTmpDir>")
//      System.exit(1)
//    }
//    val Array(dfsUri,fromDir,tmpDir) = args
        val dfsUri = "hdfs://10.4.1.4:9000"
        val fromDir ="/test/dw/2015/10/28/topic_common_event/13"
        val tmpDir = "/test/tmp"


    val sparkConf = new SparkConf()
        sparkConf.setAppName("spark-crushfile").setMaster("local")
//    sparkConf.setAppName("spark-crushfile")
    val sc = new SparkContext(sparkConf)
    sc.hadoopConfiguration.set("fs.defaultFS",dfsUri)
    val fs = FileSystem.get(sc.hadoopConfiguration)
    val sql = new SQLContext(sc)

    val df = sql.read.parquet(fromDir)
    df.registerTempTable("tmp")
    val t = sql.sql("select data from tmp")
    t.registerTempTable("data")
//    val data = sql.sql("select position from data")
      t.map(j => {
        val m = JSON.parseFull(j.toString()) match {
          case Some(map: Map[String, Any]) => {
            val pos = map.get("position").get.asInstanceOf[Map[String, String]]
            if (pos != None) {
              if (pos.get("lon") != None && pos.get("lat") != None) {
                pos.get("lat").get.asInstanceOf[Double] + "_" + pos.get("lon").get.asInstanceOf[Double]
              }
            }
          }
        }
      }).collect().foreach(println)
//    println(df.select("data").columns.toString)
//    df.repartition(1).write.format("parquet").mode(SaveMode.Overwrite).save(tmpDir)

  }
}
