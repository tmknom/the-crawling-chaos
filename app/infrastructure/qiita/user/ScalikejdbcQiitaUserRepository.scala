package infrastructure.qiita.user

import javax.inject.Singleton

import domain.qiita.user._
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserRepository extends QiitaUserRepository {
  override def register(qiitaUserName: QiitaUserName, registeredDateTime: RegisteredDateTime)(implicit session: DBSession = AutoSession): Unit = {
    val userName   = qiitaUserName.value
    val registered = registeredDateTime.value
    sql"INSERT INTO qiita_users (user_name, registered_date_time) VALUES ($userName, $registered);".update.apply()

    () // 明示的に Unit を返す
  }

  def retrieveAll()(implicit session: DBSession = AutoSession): Seq[QiitaUser] = {
    sql"SELECT id, user_name, registered_date_time FROM qiita_users ORDER BY id ASC;".map { rs =>
      QiitaUser(
        QiitaUserId(rs.int("id")),
        QiitaUserName(rs.string("user_name")),
        RegisteredDateTime(rs.zonedDateTime("registered_date_time"))
      )
    }.list().apply()
  }
}
