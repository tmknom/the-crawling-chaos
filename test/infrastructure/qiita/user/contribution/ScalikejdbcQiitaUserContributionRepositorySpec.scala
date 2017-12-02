package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserId
import domain.qiita.user.contribution.QiitaUserContribution
import fixture.db.qiita.QiitaUsersTableFixture
import library.test.db.{DatabaseSpec, FixtureDefinition}
import org.scalatest.OptionValues

class ScalikejdbcQiitaUserContributionRepositorySpec extends DatabaseSpec with OptionValues {
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
}
