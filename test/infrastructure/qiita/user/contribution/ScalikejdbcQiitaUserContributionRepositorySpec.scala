package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}
import domain.qiita.user.event.{EventDateTime, QiitaUserContributionCrawledEvent}
import fixture.db.qiita.{DeprecatedQiitaUsersFixture, QiitaUsersFixture}
import library.datetime.DateTimeProvider
import library.test.db.{DatabaseFixture, DatabaseSpec}

// scalastyle:off magic.number
class ScalikejdbcQiitaUserContributionRepositorySpec extends DatabaseSpec {
  val sut = new ScalikejdbcQiitaUserContributionRepository()

  "#register" should {
    "登録できること" in { implicit session =>
      DatabaseFixture.setup(QiitaUsersFixture.Default.Fixtures)

      val crawledEvent = QiitaUserContributionCrawledEvent(
        qiitaUserName         = QiitaUserName(DeprecatedQiitaUsersFixture.Default.UserName),
        qiitaUserContribution = QiitaUserContribution(1234),
        articlesCount         = ArticlesCount(123),
        eventDateTime         = EventDateTime(DateTimeProvider.nowJST())
      )

      sut.register(crawledEvent)
    }
  }
}
