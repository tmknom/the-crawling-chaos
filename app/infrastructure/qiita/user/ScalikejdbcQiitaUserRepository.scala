package infrastructure.qiita.user

import domain.qiita.user.{QiitaUser, QiitaUserId, QiitaUserName, QiitaUserRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
final class ScalikejdbcQiitaUserRepository extends QiitaUserRepository {
  override def register(qiitaUser: QiitaUser)(implicit session: DBSession = AutoSession): Unit = {
    val userName = qiitaUser.name.value
    sql"INSERT INTO qiita_users (user_name) VALUES ($userName);".update.apply()

    () // 明示的に Unit を返す
  }

  def retrieveAll()(implicit session: DBSession = AutoSession): Seq[QiitaUser] = {
    sql"SELECT id, user_name FROM qiita_users ORDER BY id ASC;".map { rs =>
      QiitaUser(QiitaUserId(rs.int("id")), QiitaUserName(rs.string("user_name")))
    }.list().apply()
  }
}
