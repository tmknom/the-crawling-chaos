package infrastructure.qiita.user.summary

import javax.inject.Singleton

import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}
import domain.qiita.user.summary.{QiitaUserSummary, QiitaUserSummaryRepository}
import domain.qiita.user.{QiitaUserId, QiitaUserName}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserSummaryRepository extends QiitaUserSummaryRepository {
  def retrieveTop200()(implicit session: DBSession = AutoSession): List[QiitaUserSummary] = {
    sql"""
          SELECT qu.id, qu.user_name, quc.contribution, quc.articles_count
          FROM deprecated_qiita_user_contributions AS quc
          INNER JOIN deprecated_qiita_users AS qu
          ON quc.qiita_user_id = qu.id
          ORDER BY quc.contribution DESC
          LIMIT 200;
       """
      .map(toQiitaUserSummary)
      .list()
      .apply()
  }

  private def toQiitaUserSummary(rs: WrappedResultSet): QiitaUserSummary = {
    QiitaUserSummary(
      QiitaUserId(rs.int("id")),
      QiitaUserName(rs.string("user_name")),
      QiitaUserContribution(rs.int("contribution")),
      ArticlesCount(rs.int("articles_count"))
    )
  }
}
