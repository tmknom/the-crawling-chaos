package presentation.controller

import application.qiita.user.QiitaUserContributionApplication
import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}
import domain.qiita.user.summary.QiitaUserSummary
import domain.qiita.user.{QiitaUserId, QiitaUserName}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.http.Status
import play.api.inject._
import play.api.inject.guice._
import play.api.test.Helpers._
import play.api.test._

// scalastyle:off magic.number
class QiitaUserContributionControllerSpec extends PlaySpec with GuiceOneAppPerSuite with MockitoSugar {

  "QiitaUserContributionController#list" should {

    val expected =
      """[{
        |  "id": 123,
        |  "name": "jojo",
        |  "contribution": 987
        |}]""".stripMargin

    "コントローラクラス単体の場合" in {
      val controller = new QiitaUserContributionController(stubControllerComponents(), MockBuilder.buildQiitaUserContributionApplication())
      val result     = controller.list()(FakeRequest())
      contentAsString(result) must equal(expected)
    }

    "routes含めた場合" in {
      val home = route(MockBuilder.build(), FakeRequest(GET, "/qiita/users/contributions")).get

      status(home) mustBe Status.OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must equal(expected)
    }

  }

  object MockBuilder {
    def build(): Application = {
      new GuiceApplicationBuilder().overrides(bind[QiitaUserContributionApplication].toInstance(buildQiitaUserContributionApplication())).build
    }

    def buildQiitaUserContributionApplication(): QiitaUserContributionApplication = {
      val application = mock[QiitaUserContributionApplication]
      when(application.list()).thenReturn(List(QiitaUserSummary(QiitaUserId(123), QiitaUserName("jojo"), QiitaUserContribution(987), ArticlesCount(123))))
      application
    }
  }

}
