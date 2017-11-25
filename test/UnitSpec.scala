import library.controller.HealthCheckController
import org.scalatestplus.play._
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Unit tests can run without a full Play application.
  */
class UnitSpec extends PlaySpec {

  "HealthCheckController" should {
    "return a valid result with action" in {
      val expected   = "{\"status\":\"ok\"}"
      val controller = new HealthCheckController(stubControllerComponents())
      val result     = controller.index(FakeRequest())
      contentAsString(result) must equal(expected)
    }
  }

}
