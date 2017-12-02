package infrastructure.qiita.user

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.bind.DefaultBinderConfiguration
import com.ninja_squad.dbsetup.operation.Operation
import domain.qiita.user.{QiitaUser, QiitaUserId, QiitaUserName}
import library.test.db.DatabaseSpec
import org.scalatest.OptionValues
import scalikejdbc.DBSession

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
      setupFixture(
        Operations
          .insertInto("qiita_users")
          .columns("id", "user_name")
          .values("1", "jojo")
          .values("2", "dio")
          .values("3", "kira")
          .build()
      )

      val sut    = new ScalikejdbcQiitaUserRepository()
      val actual = sut.retrieveAll()

      actual.size mustBe 3
      actual.headOption.value.name mustBe QiitaUserName("jojo")
    }
  }

  /**
    * データベースフィクスチャのセットアップ
    *
    * 通常、DbSetup を使う場合は DbSetup#launch() メソッドを呼び出すが、
    * コイツは内部的にコミット処理まで行っており AutoRollback によるロールバックが無効化される。
    *
    * そのため DbSetup#launch() メソッドから、コミット処理部分を取り除いたメソッドを独自実装し、
    * データベースフィクスチャのセットアップは本メソッドを介して行うこととする。
    *
    * @see https://qiita.com/yo1000/items/a7c0825035b4fbcfa3b2
    */
  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  def setupFixture(operations: Operation*)(implicit session: DBSession): Unit = {
    val connection = session.connection
    connection.setAutoCommit(false)

    val operation = Operations.sequenceOf(operations: _*)
    operation.execute(connection, DefaultBinderConfiguration.INSTANCE)
  }
}
