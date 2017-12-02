package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserId
import domain.qiita.user.contribution.QiitaUserContribution
import fixture.db.qiita.{QiitaUserContributionsTableFixture, QiitaUsersTableFixture}
import library.test.DatabaseSpec
import library.test.db.FixtureDefinition

class ScalikejdbcQiitaUserContributionRepositorySpec extends DatabaseSpec {
  "#register" should {
    "登録できること" in { implicit session =>
      FixtureDefinition.define(QiitaUsersTableFixture.Default.One)

      val qiitaUserId           = QiitaUserId(QiitaUsersTableFixture.Default.Id.toInt)
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
        QiitaUsersTableFixture.Default.One,
        QiitaUserContributionsTableFixture.Default.One
      )

      val sut = new ScalikejdbcQiitaUserContributionRepository()

      val qiitaUserId = QiitaUserId(QiitaUsersTableFixture.Default.Id.toInt)
      val actual      = sut.retrieve(qiitaUserId)

      val expected = QiitaUserContribution(QiitaUserContributionsTableFixture.Default.Contribution.toInt)
      actual mustBe expected
    }
  }
}
