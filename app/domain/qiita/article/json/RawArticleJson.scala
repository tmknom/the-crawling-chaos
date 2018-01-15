package domain.qiita.article.json

import domain.qiita.article._
import domain.qiita.article.contribution.{CommentsCount, LikesCount}
import domain.qiita.user.QiitaUserName
import spray.json.DefaultJsonProtocol._
import spray.json._

final case class RawArticleJson(value: String) {
  def toQiitaArticle: QiitaArticle = {
    QiitaArticle(
      QiitaArticleId.undefined,
      QiitaItemId(parseJsonString("uuid")),
      QiitaArticleTitle(parseJsonString("title")),
      QiitaArticleUrl(parseJsonString("showUrl")),
      QiitaArticlePostedDateTime(parseJsonInt("createdAtInUnixtime")),
      QiitaUserName(parseJsValue("author").asJsObject.getFields("urlName").head.convertTo[String])
    )
  }

  private def parseJsonInt(keyName: String): Int = {
    parseJsValue(keyName).convertTo[Int]
  }

  private def parseJsonString(keyName: String): String = {
    parseJsValue(keyName).convertTo[String]
  }

  private def parseJsValue(keyName: String): JsValue = {
    JsonParser(value).asJsObject.getFields(keyName).head
  }

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
