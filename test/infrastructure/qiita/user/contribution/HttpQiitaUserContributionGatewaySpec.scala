package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.QiitaUserContribution
import library.scalaj._
import org.scalatestplus.play.PlaySpec

class HttpQiitaUserContributionGatewaySpec extends PlaySpec {
  "#fetch" should {
    "通信できること" in {
      val sut = new HttpQiitaUserContributionGateway(new MockScalajHttpAdaptor())

      val actual = sut.fetch(QiitaUserName("dummy_user"))
      actual mustBe QiitaUserContribution(1234) // scalastyle:off
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
