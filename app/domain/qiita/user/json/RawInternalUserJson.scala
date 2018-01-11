package domain.qiita.user.json

import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}
import domain.qiita.user.event.{EventDateTime, QiitaUserContributionCrawledEvent}
import domain.qiita.user.{ProfileImageUrl, QiitaUser, QiitaUserId, QiitaUserName}
import spray.json.DefaultJsonProtocol._
import spray.json._

final case class RawInternalUserJson(value: String) {
  def toQiitaUser: QiitaUser = {
    QiitaUser(
      QiitaUserId(parseJsonInt("id")),
      QiitaUserName(parseJsonString("url_name")),
      ProfileImageUrl(parseJsonString("profile_image_url"))
    )
  }

  def toCrawledEvent: QiitaUserContributionCrawledEvent = {
    QiitaUserContributionCrawledEvent(
      QiitaUserName(parseJsonString("url_name")),
      QiitaUserContribution(parseJsonInt("contribution")),
      ArticlesCount(parseJsonInt("articles_count")),
      EventDateTime.now()
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
}
