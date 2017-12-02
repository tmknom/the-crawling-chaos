package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserId
import domain.qiita.user.contribution.QiitaUserContribution
import fixture.db.qiita.{QiitaUserContributionsFixture, QiitaUsersFixture}
import library.test.{DatabaseSpec, FixtureDefinition}

class ScalikejdbcQiitaUserContributionRepositorySpec extends DatabaseSpec {
  "#register" should {
    "登録できること" in { implicit session =>
      FixtureDefinition.define(QiitaUsersFixture.Default.Fixtures)

      val qiitaUserId           = QiitaUserId(QiitaUsersFixture.Default.Id.toInt)
      val qiitaUserContribution = QiitaUserContribution(100) // scalastyle:off

      val sut = new ScalikejdbcQiitaUserContributionRepository()
      sut.register(qiitaUserId, qiitaUserContribution)

      val actual = sut.retrieve(qiitaUserId)
      actual mustBe qiitaUserContribution
    }
  }

  "#retrieve" should {
    "参照できること" in { implicit session =>
      FixtureDefinition.define(
        QiitaUsersFixture.Default.Fixtures,
        QiitaUserContributionsFixture.Default.Fixtures
      )

      val sut = new ScalikejdbcQiitaUserContributionRepository()

      val qiitaUserId = QiitaUserId(QiitaUsersFixture.Default.Id.toInt)
      val actual      = sut.retrieve(qiitaUserId)

      val expected = QiitaUserContribution(QiitaUserContributionsFixture.Default.Contribution.toInt)
      actual mustBe expected
    }
  }
}
