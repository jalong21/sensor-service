package unit.controllers

import akka.stream.Materializer
import akka.util.Timeout
import controllers.HealthController
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Helpers.{GET, contentAsString}
import play.api.test._

import scala.concurrent.duration.DurationInt

class HealthControllerSpec extends PlaySpec with MockitoSugar with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer
  implicit val timeout: Timeout = Timeout(5.seconds)

  "HealthController" should {
    "return true from health endpoint" in {

      val request = FakeRequest(GET, "/v1/health")
      val healthController = new HealthController(Helpers.stubControllerComponents())
      val response = healthController.index()(request)
      contentAsString(response) mustEqual("true")
    }
  }
}
