package infrastructure.qiita.user.summary

import javax.inject.Singleton

import domain.qiita.user.contribution.QiitaUserContribution
import domain.qiita.user.summary.{QiitaUserSummary, QiitaUserSummaryRepository}
import domain.qiita.user.{QiitaUserId, QiitaUserName}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserSummaryRepository extends QiitaUserSummaryRepository {
  def retrieveTop200()(implicit session: DBSession = AutoSession): List[QiitaUserSummary] = {
    // scalastyle:off
    sql"SELECT qu.id, qu.user_name, quc.contribution FROM qiita_user_contributions AS quc INNER JOIN qiita_users AS qu ON quc.qiita_user_id = qu.id ORDER BY quc.contribution DESC LIMIT 20;".map { // scalastyle:off
      rs =>
        QiitaUserSummary(QiitaUserId(rs.int("id")), QiitaUserName(rs.string("user_name")), QiitaUserContribution(rs.int("contribution")))
    }.list().apply()
  }
}
