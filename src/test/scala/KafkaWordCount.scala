import java.util.HashMap

import org.apache.kafka.clients.producer.{ProducerConfig, KafkaProducer, ProducerRecord}

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

/**
 * Created by root on 15-10-23.
 */
object KafkaWordCount {
  def main(args: Array[String]) {
//    if (args.length < 4) {
//      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
//      System.exit(1)
//    }
//
//
//    val Array(zkQuorum, group, topics, numThreads) = args
    val zkQuorum = "10.4.1.221:2181,10.4.1.222:2181,10.4.1.223:2181"
    val group = "mytest"
    val topics = "topic_comment_event"
    val numThreads =1

    val sparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local")
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
//    val wordCounts = words.map(x => (x, 1L))
//      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)

    val t = lines.reduce(_+"\n"+_)
    t.print()

//    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
