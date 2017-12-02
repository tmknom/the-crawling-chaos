package infrastructure.qiita.user

import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.{DbSetup, Operations}
import domain.qiita.user.{QiitaUser, QiitaUserId, QiitaUserName}
import library.test.db.DatabaseSpec
import org.scalatest.OptionValues
import play.api.db.DBApi

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

  "ScalikejdbcQiitaUserRepository#retrieveAll" should {
    "一覧できること" in { implicit session =>
      val dataSource  = app.injector.instanceOf[DBApi].database("default").dataSource
      val destination = new DataSourceDestination(dataSource)
      val operations = Operations.sequenceOf(
        Operations
          .insertInto("qiita_users")
          .columns("id", "user_name")
          .values("1", "jojo")
          .values("2", "dio")
          .values("3", "kira")
          .build()
      )
      val dbSetup = new DbSetup(destination, operations)
      dbSetup.launch()

      val sut    = new ScalikejdbcQiitaUserRepository()
      val actual = sut.retrieveAll()

      actual.size mustBe 3
      actual.headOption.value.name mustBe QiitaUserName("jojo")
    }
  }
}
