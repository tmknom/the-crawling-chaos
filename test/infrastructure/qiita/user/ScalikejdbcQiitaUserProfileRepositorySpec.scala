package infrastructure.qiita.user

import domain.qiita.user.{ProfileImageUrl, QiitaUserId, QiitaUserName, QiitaUserProfile}
import fixture.db.qiita.QiitaUsersFixture
import library.test.db.{DatabaseFixture, DatabaseSpec}

// scalastyle:off magic.number
class ScalikejdbcQiitaUserProfileRepositorySpec extends DatabaseSpec {
  val sut = new ScalikejdbcQiitaUserProfileRepository()

  "#register" should {
    "登録できること" in { implicit session =>
      val qiitaUserProfile = QiitaUserProfile(
        id              = QiitaUserId(QiitaUsersFixture.Default.QiitaUserId.toInt),
        name            = QiitaUserName(QiitaUsersFixture.Default.UserName),
        profileImageUrl = ProfileImageUrl(QiitaUsersFixture.Default.ProfileImageUrl)
      )

      sut.register(qiitaUserProfile)

      val actual = sut.retrieve(qiitaUserProfile.name)
      actual mustBe Some(qiitaUserProfile)
    }
  }

  "#retrieve" should {
    "参照できること" in { implicit session =>
      DatabaseFixture.setup(QiitaUsersFixture.Default.Fixtures)

      val name   = QiitaUserName(QiitaUsersFixture.Default.UserName)
      val actual = sut.retrieve(name)

      val expected = QiitaUserProfile(
        id              = QiitaUserId(QiitaUsersFixture.Default.QiitaUserId.toInt),
        name            = QiitaUserName(QiitaUsersFixture.Default.UserName),
        profileImageUrl = ProfileImageUrl(QiitaUsersFixture.Default.ProfileImageUrl)
      )
      actual mustBe Some(expected)
    }
  }
}
