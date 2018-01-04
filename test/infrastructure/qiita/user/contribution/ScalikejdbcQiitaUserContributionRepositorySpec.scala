package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserId
import domain.qiita.user.contribution.{QiitaUserContribution, UpdatedDateTime}
import fixture.db.qiita.{QiitaUserContributionsFixture, QiitaUsersFixture}
import library.test.db.{DatabaseFixture, DatabaseSpec}

// scalastyle:off magic.number
class ScalikejdbcQiitaUserContributionRepositorySpec extends DatabaseSpec {
  val sut = new ScalikejdbcQiitaUserContributionRepository()

  "#register" should {
    "登録できること" in { implicit session =>
      DatabaseFixture.setup(QiitaUsersFixture.Default.Fixtures)

      val qiitaUserId           = QiitaUserId(QiitaUsersFixture.Default.Id.toInt)
      val qiitaUserContribution = QiitaUserContribution(100)

      sut.register(qiitaUserId, qiitaUserContribution, UpdatedDateTime.now())

      val actual = sut.retrieve(qiitaUserId)
      actual mustBe qiitaUserContribution
    }
  }

  "#retrieve" should {
    "参照できること" in { implicit session =>
      DatabaseFixture.setup(
        QiitaUsersFixture.Default.Fixtures,
        QiitaUserContributionsFixture.Default.Fixtures
      )

      val qiitaUserId = QiitaUserId(QiitaUsersFixture.Default.Id.toInt)
      val actual      = sut.retrieve(qiitaUserId)

      val expected = QiitaUserContribution(QiitaUserContributionsFixture.Default.Contribution.toInt)
      actual mustBe expected
    }
  }
}
