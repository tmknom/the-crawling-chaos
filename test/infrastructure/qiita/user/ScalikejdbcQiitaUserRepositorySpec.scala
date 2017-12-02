package infrastructure.qiita.user

import domain.qiita.user.{QiitaUser, QiitaUserId, QiitaUserName}
import library.test.db.DatabaseSpec
import org.scalatest.OptionValues

class ScalikejdbcQiitaUserRepositorySpec extends DatabaseSpec with OptionValues {
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
}