package infrastructure.qiita.user

import domain.qiita.user.{ProfileImageUrl, QiitaUser, QiitaUserId, QiitaUserName}
import fixture.db.qiita.QiitaUsersFixture
import library.test.db.{DatabaseFixture, DatabaseSpec}

// scalastyle:off magic.number
class ScalikejdbcQiitaUserRepositorySpec extends DatabaseSpec {
  val sut = new ScalikejdbcQiitaUserRepository()

  "#register" should {
    "登録できること" in { implicit session =>
      val qiitaUser = QiitaUser(
        id              = QiitaUserId(QiitaUsersFixture.Default.QiitaUserId.toInt),
        name            = QiitaUserName(QiitaUsersFixture.Default.UserName),
        profileImageUrl = ProfileImageUrl(QiitaUsersFixture.Default.ProfileImageUrl)
      )

      sut.register(qiitaUser)

      val actual = sut.retrieve(qiitaUser.name)
      actual mustBe Some(qiitaUser)
    }
  }

  "#retrieve" should {
    "参照できること" in { implicit session =>
      DatabaseFixture.setup(QiitaUsersFixture.Default.Fixtures)

      val name   = QiitaUserName(QiitaUsersFixture.Default.UserName)
      val actual = sut.retrieve(name)

      val expected = QiitaUser(
        id              = QiitaUserId(QiitaUsersFixture.Default.QiitaUserId.toInt),
        name            = QiitaUserName(QiitaUsersFixture.Default.UserName),
        profileImageUrl = ProfileImageUrl(QiitaUsersFixture.Default.ProfileImageUrl)
      )
      actual mustBe Some(expected)
    }
  }
}
