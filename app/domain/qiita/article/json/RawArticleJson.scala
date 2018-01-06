package domain.qiita.article.json

import domain.qiita.article.contribution.{CommentsCount, LikesCount}
import spray.json.DefaultJsonProtocol._
import spray.json._

final case class RawArticleJson(value: String) {
  def toJsonMap: Map[String, JsValue] = {
    JsonParser(value).asJsObject.fields
  }

  def toLikesCount: LikesCount = {
    LikesCount(parseJson[Int](toJsonMap, "likesCount"))
  }

  def toCommentsCount: CommentsCount = {
    CommentsCount(parseJson[Int](toJsonMap, "commentsCount"))
  }

  private def parseJson[T: JsonReader](jsonMap: Map[String, JsValue], keyName: String): T = {
    jsonMap.get(keyName) match {
      case Some(value) => value.convertTo[T]
      case None        => throw new RuntimeException(s"Not found $keyName.")
    }
  }
}
