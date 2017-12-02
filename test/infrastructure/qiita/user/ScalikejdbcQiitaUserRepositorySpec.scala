package infrastructure.qiita.user

import domain.qiita.user.{QiitaUser, QiitaUserId, QiitaUserName}
import fixture.db.qiita.QiitaUsersFixture
import library.test.{DatabaseSpec, FixtureDefinition}

class ScalikejdbcQiitaUserRepositorySpec extends DatabaseSpec {
  "ScalikejdbcQiitaUserRepository#register" should {
    "登録できること" in { implicit session =>
      val user = QiitaUser(QiitaUserId(100), QiitaUserName("jojo")) // scalastyle:off

      val sut = new ScalikejdbcQiitaUserRepository()
      sut.register(user)

      val actual = sut.retrieveAll()
      actual.size mustBe 1
      actual.headOption.value.name mustBe QiitaUserName("jojo")
    }
  }

  "ScalikejdbcQiitaUserRepository#retrieveAll" should {
    "一覧できること" in { implicit session =>
      FixtureDefinition.define(QiitaUsersFixture.List.Fixtures)

      val sut    = new ScalikejdbcQiitaUserRepository()
      val actual = sut.retrieveAll()

      actual.size mustBe 3
      actual.headOption.value.name mustBe QiitaUserName("jojo")
    }
  }
}
