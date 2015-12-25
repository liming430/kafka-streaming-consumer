package com.caishi.kafka.customer

import java.util.Calendar
import java.text.SimpleDateFormat

import kafka.serializer.StringDecoder
import org.apache.spark.mllib.clustering.StreamingKMeans
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{SaveMode, SQLContext}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.{SparkContext, SparkConf}

import scala.util.parsing.json.JSON
import com.caishi.kafka.model._

/**
 * Created by root on 15-10-12.
 * kafka consumer real time to hdfs
 */
object Common {
  def main(args: Array[String]): Unit = {
    if (args.length < 6) {
      System.err.println("Usage: Common <zkQuorum> <brokers> <topics> <timeWindow> <numRepartition> <pathPre:hdfs pre >")
      System.exit(1)
    }
//    offline zk: 10.10.42.24:2181,10.10.42.25:2128,10.10.42.24:2128
    val Array(zkQuorum, brokers, topics, timeWindow, numRepartition,pathPre) = args
//    val zkQuorum:String = "10.4.1.221:2181,10.4.1.222:2181,10.4.1.223:2181"
//    val zkQuorum:String = "10.10.42.24:2181,10.10.42.25:2128,10.10.42.24:2128"
//    val brokers : String = "10.4.1.201:9092,10.4.1.202:9092,10.4.1.203:9092"
//    val topics : String = "topic_comment_event,topic_news_behavior,topic_news_social,topic_common_event,topic_scene_behavior"
//    val topics : String = "topic_common_event"
//    val timeWindow : Int = 30
//    val numRepartition : Int = 6
//    val pathPre : String ="hdfs://10.4.1.4:9000/test/dw"
    val sparkConf = new SparkConf().setAppName("spark-original-log")
    sparkConf.set("spark.streaming.kafka.maxRatePerPartition","10000")
    val ssc = new StreamingContext(sparkConf, Seconds(timeWindow.toInt))

//    ssc.sparkContext.setLocalProperty("spark.scheduler.pool","production")
    // Kafka configurations
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> brokers,
      "serializer.class" -> "kafka.serializer.StringEncoder",
      "group.id" -> "spark_streaming",
      "auto.offset.reset" -> "smallest"
      )

    // Since Spark 1.3 we can use Direct Stream
    val topicsSet = topics.split(",").toSet
    val km = new KafkaManager(kafkaParams)
    val data = km.createDirectStream[String, String, StringDecoder, StringDecoder](ssc,kafkaParams,topicsSet)

    val lines = data.repartition(numRepartition.toInt).map(_._2)

    val commentData = lines.filter(checkDataType(_,"topic_comment_event")).map(Util.convertToJson(_,1)).foreachRDD(rdd => saveToParquet(rdd,"topic_comment_event",pathPre))
    val newsBehaviorData = lines.filter(checkDataType(_,"topic_news_behavior")).map(Util.convertToJson(_,2)).foreachRDD(rdd => saveToParquet(rdd,"topic_news_behavior",pathPre))
    val newsSocialData = lines.filter(checkDataType(_,"topic_news_social")).map(Util.convertToJson(_,3)).foreachRDD(rdd => saveToParquet(rdd,"topic_news_social",pathPre))
    val commonEventData = lines.filter(checkDataType(_,"topic_common_event")).map(Util.convertToJson(_,4)).foreachRDD(rdd => saveToParquet(rdd,"topic_common_event",pathPre))
    val sceneBehaviorData = lines.filter(checkDataType(_,"topic_scene_behavior")).map(Util.convertToJson(_,5)).foreachRDD(rdd => saveToParquet(rdd,"topic_scene_behavior",pathPre))
    val recommendationData = lines.filter(checkDataType(_,"topic_recommendation_event")).map(Util.convertToJson(_,6)).foreachRDD(rdd => saveToParquet(rdd,"topic_recommendation_event",pathPre))
    val chooseLikeData = lines.filter(checkDataType(_,"topic_personalization_event")).map(Util.convertToJson(_,7)).foreachRDD(rdd => saveToParquet(rdd,"topic_personalization_event",pathPre))

//    更新kafka 监控offset值
    data.foreachRDD(rdd => {
      if(!rdd.isEmpty()){
        //更新zk offset
        km.updateZKOffsets(rdd)
      }
    })
    ssc.start()
    ssc.awaitTermination()
  }

  def checkDataType(d : String,dataType:String): Boolean ={
    val s = JSON.parseFull(d)
    val isType:Boolean = s match {
      case Some(map: Map[String, Any]) =>{
        try {
          dataType.equals(map("topic"))
        }catch {
          case e => false
        }
      }
    }
    isType
  }

  def saveToParquet(rdd: RDD[String], dataType: String,pathPre : String): Unit = {
    val sqlContext = SQLContextSingleton.getInstance(rdd.sparkContext)
    sqlContext.setConf("parquet.enable.summary-metadata", "false")

    // Loads an `JavaRDD[String]` storing JSON objects (one object per record)
    val df = sqlContext.read.json(rdd)
    if(df.count() > 0){
      val dirname = {
        dataType match {
          case "topic_comment_event" => dirName.getDirName(pathPre,"topic_comment_event")
          case "topic_news_behavior" => dirName.getDirName(pathPre,"topic_news_behavior")
          case "topic_news_social" => dirName.getDirName(pathPre,"topic_news_social")
          case "topic_common_event" => dirName.getDirName(pathPre,"topic_common_event")
          case "topic_scene_behavior" => dirName.getDirName(pathPre,"topic_scene_behavior")
          case "topic_recommendation_event" => dirName.getDirName(pathPre,"topic_recommendation_event")
          case "topic_personalization_event" => dirName.getDirName(pathPre,"topic_personalization_event")
        }
      }
      try {
        df.write.format("parquet").mode(SaveMode.Append).save(dirname)
      }catch {
        case e: Throwable =>
          println("ERROR: Save to parquet error\n" + e.toString + "\n" + rdd.collect())
      }
    }
  }
}

/* 计算文件存放目录 */
object dirName {
  def getDirName(pathPre : String, dataType: String): String = {
    val time = Calendar.getInstance().getTime
    val year = new SimpleDateFormat("yyyy").format(time)
    val month = new SimpleDateFormat("MM").format(time)
    val day = new SimpleDateFormat("dd").format(time)
    val hour = new SimpleDateFormat("HH").format(time)
    val minute = new SimpleDateFormat("mm").format(time)
    val filename = (pathPre+"/%s/%s/%s/" + dataType + "/%s").format(year, month, day, hour)
    filename
  }
}

/** Lazily instantiated singleton instance of SQLContext */
object SQLContextSingleton {
  @transient  private var instance: SQLContext = _
  def getInstance(sparkContext: SparkContext): SQLContext = {
    if (instance == null) {
      instance = new SQLContext(sparkContext)
      instance.setConf("spark.sql.parquet.compression.codec", "snappy")
    }
    instance
  }
}

