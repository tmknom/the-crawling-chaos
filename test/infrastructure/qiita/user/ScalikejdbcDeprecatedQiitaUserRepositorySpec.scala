package infrastructure.qiita.user

import domain.qiita.user.{QiitaUserName, RegisteredDateTime}
import fixture.db.qiita.DeprecatedQiitaUsersFixture
import library.test.db.{DatabaseFixture, DatabaseSpec}

// scalastyle:off magic.number
class ScalikejdbcDeprecatedQiitaUserRepositorySpec extends DatabaseSpec {
  val sut = new ScalikejdbcDeprecatedQiitaUserRepository()

  "ScalikejdbcQiitaUserRepository#register" should {
    "登録できること" in { implicit session =>
      sut.register(QiitaUserName("jojo"), RegisteredDateTime.now())

      val actual = sut.retrieveRecently()
      actual.size mustBe 1
      actual.headOption.value.name mustBe QiitaUserName("jojo")
    }
  }

  "ScalikejdbcQiitaUserRepository#retrieveAll" should {
    "一覧できること" in { implicit session =>
      DatabaseFixture.setup(DeprecatedQiitaUsersFixture.List.Fixtures)
      val actual = sut.retrieveRecently()

      actual.size mustBe 3
      actual.headOption.value.name mustBe QiitaUserName("jojo")
    }
  }
}
