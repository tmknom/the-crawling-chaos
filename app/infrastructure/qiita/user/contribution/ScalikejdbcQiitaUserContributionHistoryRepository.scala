package infrastructure.qiita.user.contribution

import javax.inject.Singleton

import domain.qiita.user.RegisteredDateTime
import domain.qiita.user.contribution.QiitaUserContributionHistoryRepository
import domain.qiita.user.summary.QiitaUserSummary
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserContributionHistoryRepository extends QiitaUserContributionHistoryRepository {

  def register(qiitaUserSummary: QiitaUserSummary, registeredDateTime: RegisteredDateTime)(implicit session: DBSession = AutoSession): Int = {
    val id             = qiitaUserSummary.id.value
    val contribution   = qiitaUserSummary.contribution.value
    val articlesCount  = qiitaUserSummary.articlesCount.value
    val registeredDate = registeredDateTime.toLocalDate
    val registered     = registeredDateTime.value
    sql"""
          INSERT INTO qiita_user_contribution_histories
          (qiita_user_id, contribution, articles_count, registered_date, registered_date_time)
          VALUES ($id, $contribution, $articlesCount, $registeredDate, $registered);
       """
      .update()
      .apply()
  }
}
