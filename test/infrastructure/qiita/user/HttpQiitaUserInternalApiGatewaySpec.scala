package infrastructure.qiita.user

import domain.qiita.user._
import library.scalaj.{HttpHeaders, HttpParams, HttpTimeout, ScalajHttpAdaptor}
import org.scalatestplus.play.PlaySpec

// scalastyle:off magic.number
class HttpQiitaUserInternalApiGatewaySpec extends PlaySpec {
  val sut = new HttpQiitaUserInternalApiGateway(new MockScalajHttpAdaptor())

  "#fetch" should {
    "通信できること" in {
      val actual = sut.fetch(QiitaUserName("jojo"))

      val expected = QiitaUserProfile(
        QiitaUserId(123456789),
        QiitaUserName("jojo"),
        ProfileImageUrl("https://qiita-image-store.s3.amazonaws.com/xxxx")
      )

      actual.toQiitaUserProfile mustBe expected
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
