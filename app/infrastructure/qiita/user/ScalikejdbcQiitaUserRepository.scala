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
    sql"SELECT id, user_name, registered_date_time FROM qiita_users ORDER BY id ASC;"
      .map(toQiitaUser)
      .list()
      .apply()
  }

  // scalastyle:off
  def retrieveContributed()(implicit session: DBSession = AutoSession): Seq[QiitaUser] = {
    sql"SELECT qu.id, qu.user_name, qu.registered_date_time FROM qiita_users AS qu INNER JOIN qiita_user_contributions AS quc ON qu.id = quc.qiita_user_id WHERE quc.contribution > 0 ORDER BY quc.contribution DESC;"
      .map(toQiitaUser)
      .list()
      .apply()
  }

  private def toQiitaUser(rs: WrappedResultSet): QiitaUser = {
    QiitaUser(
      QiitaUserId(rs.int("id")),
      QiitaUserName(rs.string("user_name")),
      RegisteredDateTime(rs.zonedDateTime("registered_date_time"))
    )
  }
}
