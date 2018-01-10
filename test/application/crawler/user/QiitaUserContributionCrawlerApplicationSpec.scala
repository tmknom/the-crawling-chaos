package application.crawler.user

import domain.qiita.user._
import domain.qiita.user.contribution._
import domain.qiita.user.summary.QiitaUserSummary
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec

// scalastyle:off magic.number
class QiitaUserContributionCrawlerApplicationSpec extends PlaySpec with MockitoSugar with BeforeAndAfter {

  private val qiitaUserSummary = QiitaUserSummary(
    id            = QiitaUserId(1),
    name          = QiitaUserName("jojo"),
    contribution  = QiitaUserContribution(1234),
    articlesCount = ArticlesCount(123)
  )

  private val mockQiitaUserContributionRepository        = mock[QiitaUserContributionRepository]
  private val mockQiitaUserContributionHistoryRepository = mock[QiitaUserContributionHistoryRepository]
  private val mockQiitaUserInternalApiGateway            = mock[DeprecatedQiitaUserInternalApiGateway]

  before {
    when(mockQiitaUserInternalApiGateway.fetch(any[DeprecatedQiitaUser])).thenReturn(qiitaUserSummary)
    when(mockQiitaUserContributionRepository.register(any[QiitaUserSummary], any[UpdatedDateTime])).thenReturn(1)
    when(mockQiitaUserContributionHistoryRepository.register(any[QiitaUserSummary], any[RegisteredDateTime])).thenReturn(1)
  }

  "QiitaUserContributionCrawlerApplication#crawl" should {
    "クロールできること" in {
      val sut = new QiitaUserContributionCrawlerApplication(
        mockQiitaUserInternalApiGateway,
        mockQiitaUserContributionRepository,
        mockQiitaUserContributionHistoryRepository
      )

      sut.crawl(Seq.empty[DeprecatedQiitaUser])
    }
  }
}
