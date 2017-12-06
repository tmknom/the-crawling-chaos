package infrastructure.qiita.user

import domain.qiita.user.{QiitaUser, QiitaUserId, QiitaUserName}
import fixture.db.qiita.QiitaUsersFixture
import library.test.{DatabaseFixture, DatabaseSpec}

// scalastyle:off magic.number
class ScalikejdbcQiitaUserRepositorySpec extends DatabaseSpec {
  val sut = new ScalikejdbcQiitaUserRepository()

  "ScalikejdbcQiitaUserRepository#register" should {
    "登録できること" in { implicit session =>
      val user = QiitaUser(QiitaUserId(100), QiitaUserName("jojo"))
      sut.register(user)

      val actual = sut.retrieveAll()
      actual.size mustBe 1
      actual.headOption.value.name mustBe QiitaUserName("jojo")
    }
  }

  "ScalikejdbcQiitaUserRepository#retrieveAll" should {
    "一覧できること" in { implicit session =>
      DatabaseFixture.define(QiitaUsersFixture.List.Fixtures)
      val actual = sut.retrieveAll()

      actual.size mustBe 3
      actual.headOption.value.name mustBe QiitaUserName("jojo")
    }
  }
}
