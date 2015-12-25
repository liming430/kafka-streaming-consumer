import _root_.kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
/**
 * Created by root on 15-10-12.
 */
object Test {
  def main(args: Array[String]) {
    val zkQuorum:String = "10.4.1.221:2181,10.4.1.222:2181,10.4.1.223:2181"
    val brokers : String = "10.4.1.202:9092,10.4.1.201:9092"
//    val topics : String = "topic_news_behavior,topic_news_social,topic_common_event,topic_scene_behavior"
    val topics : String = "topic_common_event"
    val numRepartition : Int = 2


    val sparkConf = new SparkConf().setAppName("KafkaWordCount").setMaster("local")
    val ssc = new StreamingContext(sparkConf, Seconds(10))
    ssc.checkpoint("checkpoint")
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> brokers, "serializer.class" -> "kafka.serializer.StringEncoder")

    //    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)

    val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet)

    val word = lines.map(print(_))
    word.print()
    word.saveAsTextFiles("/tmp/xxx.txxt")
    ssc.start()
    ssc.awaitTermination()
  }
}
