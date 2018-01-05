package infrastructure.qiita.article

import java.time.{Instant, ZoneId, ZonedDateTime}

import domain.qiita.article._
import domain.qiita.user.QiitaUserName
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors._
import spray.json.DefaultJsonProtocol._
import spray.json._

private[article] final case class QiitaArticleParser(html: String) {
  private val UndefinedQiitaArticleId = QiitaArticleId(-1)

  def parse: QiitaArticle = {
    val doc  = JsoupBrowser().parseString(html)
    val json = doc >> element("#main article .p-items_article") >> attr("data-article-props")

    val jsonMap  = JsonParser(json).asJsObject.fields
    val instant  = Instant.ofEpochSecond(parse[Int](jsonMap, "createdAtInUnixtime"))
    val dateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Tokyo"))
    val author   = parse[Map[String, JsValue]](jsonMap, "author")

    QiitaArticle(
      id             = UndefinedQiitaArticleId,
      itemId         = QiitaItemId(parse[String](jsonMap, "uuid")),
      title          = QiitaArticleTitle(parse[String](jsonMap, "title")),
      url            = QiitaArticleUrl(parse[String](jsonMap, "showUrl")),
      postedDateTime = QiitaArticlePostedDateTime(dateTime),
      userName       = QiitaUserName(parse[String](author, "urlName"))
    )
  }

  private def parse[T: JsonReader](jsonMap: Map[String, JsValue], keyName: String): T = {
    jsonMap.get(keyName) match {
      case Some(value) => value.convertTo[T]
      case None        => throw new RuntimeException(s"Not found $keyName.")
    }
  }
}
