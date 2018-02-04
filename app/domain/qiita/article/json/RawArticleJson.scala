package domain.qiita.article.json

import domain.qiita.article.QiitaItemId
import domain.qiita.article.markdown.{Markdown, MarkdownCrawledEvent}
import domain.qiita.user.CrawledDateTime
import spray.json.DefaultJsonProtocol._
import spray.json._

final case class RawArticleJson(value: String) {
  def toMarkdownCrawledEvent(qiitaItemId: QiitaItemId): MarkdownCrawledEvent = {
    MarkdownCrawledEvent(
      qiitaItemId,
      Markdown(parseJsonString("body")),
      CrawledDateTime.now()
    )
  }

  private def parseJsonString(keyName: String): String = {
    parseJsValue(keyName).convertTo[String]
  }

  private def parseJsValue(keyName: String): JsValue = {
    JsonParser(value).asJsObject.getFields(keyName).head
  }
}
