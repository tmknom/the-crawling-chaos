package presentation.controller

import application.qiita.user.QiitaUserApplication
import domain.qiita.user._
import domain.qiita.user.contribution.{ArticlesCount, Contribution, QiitaUserContribution}
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
class QiitaUserControllerSpec extends PlaySpec with GuiceOneAppPerSuite with MockitoSugar {

  "QiitaUserController#list" should {

    val expected =
      """[{
        |  "id": 123,
        |  "name": "jojo",
        |  "contribution": 987,
        |  "articles_count": 65
        |}]""".stripMargin

    "コントローラクラス単体の場合" in {
      val controller = new QiitaUserController(stubControllerComponents(), MockBuilder.mockQiitaUserApplication())
      val result     = controller.list()(FakeRequest())
      contentAsString(result) must equal(expected)
    }

    "routes含めた場合" in {
      val home = route(MockBuilder.app(), FakeRequest(GET, "/qiita/users")).get

      status(home) mustBe Status.OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must equal(expected)
    }

  }

  object MockBuilder {
    def app(): Application = {
      new GuiceApplicationBuilder().overrides(bind[QiitaUserApplication].toInstance(mockQiitaUserApplication())).build
    }

    def mockQiitaUserApplication(): QiitaUserApplication = {
      val application           = mock[QiitaUserApplication]
      val profile               = QiitaUserProfile(QiitaUserId(123), QiitaUserName("jojo"), ProfileImageUrl("dummy"))
      val qiitaUserContribution = QiitaUserContribution(Contribution(987), ArticlesCount(65))
      val qiitaUsers            = List(QiitaUser(profile, qiitaUserContribution))
      when(application.list()).thenReturn(qiitaUsers)
      application
    }
  }

}
