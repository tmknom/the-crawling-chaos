package infrastructure.qiita.user.contribution

import javax.inject.Singleton

import domain.qiita.user.contribution.{QiitaUserContributionCrawledEvent, QiitaUserContributionHistoryRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserContributionHistoryRepository extends QiitaUserContributionHistoryRepository {

  def register(event: QiitaUserContributionCrawledEvent)(implicit session: DBSession = AutoSession): Int = {
    val name          = event.qiitaUserName.value
    val contribution  = event.qiitaUserContribution.value
    val articlesCount = event.articlesCount.value
    val dateTime      = event.eventDateTime.value
    val date          = event.eventDateTime.toLocalDate

    sql"""
          INSERT INTO qiita_user_contribution_histories
          (user_name, contribution, articles_count, registered_date, registered_date_time)
          VALUES ($name, $contribution, $articlesCount, $date, $dateTime);
       """
      .update()
      .apply()
  }
}
