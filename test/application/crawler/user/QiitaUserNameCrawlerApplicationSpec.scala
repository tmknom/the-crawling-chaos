package application.crawler.user

import domain.qiita.user._
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

// scalastyle:off magic.number
class QiitaUserNameCrawlerApplicationSpec extends PlaySpec with MockitoSugar with BeforeAndAfter {

  private val mockQiitaUserNameRepository = mock[QiitaUserNameRepository]
  private val mockQiitaUserApiGateway     = mock[QiitaUserApiGateway]

  before {
    when(mockQiitaUserNameRepository.register(any[QiitaUserName])).thenReturn(1)
    when(mockQiitaUserApiGateway.fetch(any[Int])).thenReturn(Seq(QiitaUserName("jojo")))
  }

  "QiitaUserNameCrawlerApplication#crawl" should {
    "クロールできること" in {
      val sut = new QiitaUserNameCrawlerApplication(
        mockQiitaUserNameRepository,
        mockQiitaUserApiGateway
      )

      sut.crawl()
    }
  }
}
