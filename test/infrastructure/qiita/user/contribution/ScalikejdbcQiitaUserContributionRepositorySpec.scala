package infrastructure.qiita.user.contribution

import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution, UpdatedDateTime}
import domain.qiita.user.summary.QiitaUserSummary
import domain.qiita.user.{QiitaUserId, QiitaUserName}
import fixture.db.qiita.{DeprecatedQiitaUsersFixture, QiitaUserContributionsFixture}
import library.test.db.{DatabaseFixture, DatabaseSpec}

// scalastyle:off magic.number
class ScalikejdbcQiitaUserContributionRepositorySpec extends DatabaseSpec {
  val sut = new ScalikejdbcQiitaUserContributionRepository()

  "#register" should {
    "登録できること" in { implicit session =>
      DatabaseFixture.setup(DeprecatedQiitaUsersFixture.Default.Fixtures)

      val qiitaUserSummary = QiitaUserSummary(
        id            = QiitaUserId(DeprecatedQiitaUsersFixture.Default.Id.toInt),
        name          = QiitaUserName(DeprecatedQiitaUsersFixture.Default.UserName),
        contribution  = QiitaUserContribution(1234),
        articlesCount = ArticlesCount(123)
      )

      sut.register(qiitaUserSummary, UpdatedDateTime.now())

      val actual = sut.retrieve(qiitaUserSummary.id)
      actual mustBe qiitaUserSummary.contribution
    }
  }

  "#retrieve" should {
    "参照できること" in { implicit session =>
      DatabaseFixture.setup(
        DeprecatedQiitaUsersFixture.Default.Fixtures,
        QiitaUserContributionsFixture.Default.Fixtures
      )

      val qiitaUserId = QiitaUserId(DeprecatedQiitaUsersFixture.Default.Id.toInt)
      val actual      = sut.retrieve(qiitaUserId)

      val expected = QiitaUserContribution(QiitaUserContributionsFixture.Default.Contribution.toInt)
      actual mustBe expected
    }
  }
}
