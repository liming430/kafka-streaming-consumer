
import scala.util.parsing.json.JSON

/**
 * Created by root on 15-10-26.
 */
object Test2 {
  def main(args: Array[String]) {
    val data = """{"auditLevel":"MONITOR","auditStatus":"UNDONE","commentLevel":"GENERAL","commentStatus":"ONLINE","content":"图兔兔图兔兔","createTime":1445858401970,"deviceId":"357784059026714","deviceType":"01","forbiddenWordList":[1,2,3],"id":"0ba08b4bc91d4f1bbc3ed7f19bbfef0f","messageId":"28209bf84c32b241","messageType":"NEWS","nickName":"溜达用户7403","osType":"01","portrait":"http://meta.9liuda.com/image/comment/user/icon/center_default_icon04.png","sensitiveWordList":[],"userId":"0f8dbb917347550b0451e844b9e866a0"}"""
    val s = JSON.parseFull(data)
//    val x = s match {
//      case Some(map: Map[String, Any]) => { map}
//    }
//
//    println(x("auditLevel")+"\t"+x("forbiddenWordList"))

  }
}
