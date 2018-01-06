package infrastructure.qiita.article.json

import domain.qiita.article.contribution.FacebookCount
import spray.json.DefaultJsonProtocol._
import spray.json._

private[json] final case class FacebookParser(json: String) {
  def parse: FacebookCount = {
    val share         = JsonParser(json).asJsObject.getFields("share").head
    val facebookCount = share.asJsObject.getFields("share_count").head.convertTo[Int]
    FacebookCount(facebookCount)
  }
}
