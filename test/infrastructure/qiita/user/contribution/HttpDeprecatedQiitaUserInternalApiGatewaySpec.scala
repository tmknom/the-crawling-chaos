package infrastructure.qiita.user.contribution

import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}
import domain.qiita.user.summary.QiitaUserSummary
import domain.qiita.user.{DeprecatedQiitaUser, QiitaUserId, QiitaUserName, RegisteredDateTime}
import library.scalaj._
import org.scalatestplus.play.PlaySpec

// scalastyle:off magic.number
class HttpDeprecatedQiitaUserInternalApiGatewaySpec extends PlaySpec {
  val sut = new HttpDeprecatedQiitaUserInternalApiGateway(new MockScalajHttpAdaptor())

  "#fetch" should {
    "通信できること" in {
      val qiitaUser = DeprecatedQiitaUser(QiitaUserId(1), QiitaUserName("dummy_user"), RegisteredDateTime.now())
      val actual    = sut.fetch(qiitaUser)

      val expected = QiitaUserSummary(
        id            = qiitaUser.id,
        name          = qiitaUser.name,
        contribution  = QiitaUserContribution(1234),
        articlesCount = ArticlesCount(98)
      )
      actual mustBe expected
    }
  }

  class MockScalajHttpAdaptor extends ScalajHttpAdaptor {
    def get(url: String, httpParams: HttpParams, headers: HttpHeaders, timeout: HttpTimeout): String = {
      """
        |{
        |  "articles_count":98,
        |  "contribution":1234,
        |  "followed":false,
        |  "id":123456789,
        |  "name":"",
        |  "profile_image_url":"https://qiita-image-store.s3.amazonaws.com/xxxx",
        |  "url_name":"jojo"
        |}
      """.stripMargin
    }
  }

}
