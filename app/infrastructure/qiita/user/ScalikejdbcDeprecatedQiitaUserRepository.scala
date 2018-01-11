package infrastructure.qiita.user

import javax.inject.Singleton

import domain.qiita.user._
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcDeprecatedQiitaUserRepository extends DeprecatedQiitaUserRepository {
  override def retrieveContributed()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser] = {
    sql"""
          SELECT qu.id, qu.user_name, qu.registered_date_time FROM deprecated_qiita_users AS qu
          INNER JOIN deprecated_qiita_user_contributions AS quc ON qu.id = quc.qiita_user_id
          WHERE quc.contribution > 0
          ORDER BY quc.contribution DESC;
       """
      .map(toQiitaUser)
      .list()
      .apply()
  }

  override def retrieveTop1000()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser] = {
    sql"""
          SELECT qu.id, qu.user_name, qu.registered_date_time FROM deprecated_qiita_users AS qu
          INNER JOIN deprecated_qiita_user_contributions AS quc ON qu.id = quc.qiita_user_id
          ORDER BY quc.contribution DESC LIMIT 1000;
       """
      .map(toQiitaUser)
      .list()
      .apply()
  }

  private def toQiitaUser(rs: WrappedResultSet): DeprecatedQiitaUser = {
    DeprecatedQiitaUser(
      QiitaUserId(rs.int("id")),
      QiitaUserName(rs.string("user_name")),
      RegisteredDateTime(rs.zonedDateTime("registered_date_time"))
    )
  }
}
