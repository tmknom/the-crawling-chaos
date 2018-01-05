package infrastructure.qiita.article

import java.time.ZonedDateTime

import domain.qiita.article.QiitaArticlePostedDateTime
import domain.qiita.article.detail.QiitaArticleBody
import spray.json.DefaultJsonProtocol._
import spray.json._

private[article] final case class QiitaArticleParser(json: String) {
  def parse: (QiitaArticlePostedDateTime, QiitaArticleBody) = {
    val jsonMap = JsonParser(json).asJsObject.fields

    val postedDateTime = QiitaArticlePostedDateTime(ZonedDateTime.parse(parse[String](jsonMap, "created_at")))
    val body           = QiitaArticleBody(parse[String](jsonMap, "body"))

    (postedDateTime, body)
  }

  private def parse[T: JsonReader](jsonMap: Map[String, JsValue], keyName: String): T = {
    jsonMap.get(keyName) match {
      case Some(value) => value.convertTo[T]
      case None        => throw new RuntimeException(s"Not found $keyName.")
    }
  }
}
