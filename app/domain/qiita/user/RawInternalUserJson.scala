package domain.qiita.user

import domain.qiita.user.contribution.{ArticlesCount, Contribution, QiitaUserContributionCrawledEvent}
import spray.json.DefaultJsonProtocol._
import spray.json._

final case class RawInternalUserJson(value: String) {
  def toQiitaUserProfile: QiitaUserProfile = {
    QiitaUserProfile(
      QiitaUserId(parseJsonInt("id")),
      QiitaUserName(parseJsonString("url_name")),
      ProfileImageUrl(parseJsonString("profile_image_url"))
    )
  }

  def toCrawledEvent: QiitaUserContributionCrawledEvent = {
    QiitaUserContributionCrawledEvent(
      QiitaUserName(parseJsonString("url_name")),
      Contribution(parseJsonInt("contribution")),
      ArticlesCount(parseJsonInt("articles_count")),
      CrawledDateTime.now()
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
