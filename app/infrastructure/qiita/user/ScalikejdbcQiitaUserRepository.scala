package infrastructure.qiita.user

import domain.qiita.user.{QiitaUser, QiitaUserRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
final class ScalikejdbcQiitaUserRepository extends QiitaUserRepository {
  override def register(qiitaUser: QiitaUser)(implicit session: DBSession = AutoSession): Unit = {
    val userName = qiitaUser.name.value
    sql"INSERT INTO qiita_users (user_name) VALUES ($userName);".update.apply()
  }
}
