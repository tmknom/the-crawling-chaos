package infrastructure.qiita.user.contribution

import javax.inject.Singleton

import domain.qiita.article.contribution.HatenaCount
import domain.qiita.user.contribution.{QiitaUserContributionCrawledEvent, QiitaUserContributionHistoryRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserContributionHistoryRepository extends QiitaUserContributionHistoryRepository {

  def register(event: QiitaUserContributionCrawledEvent, hatenaCount: HatenaCount)(implicit session: DBSession = AutoSession): Int = {
    val name          = event.qiitaUserName.value
    val contribution  = event.qiitaUserContribution.contribution.value
    val articlesCount = event.qiitaUserContribution.articlesCount.value
    val hatena        = hatenaCount.value
    val dateTime      = event.crawledDateTime.value
    val date          = event.crawledDateTime.toLocalDate

    sql"""
          INSERT INTO qiita_user_contribution_histories
          (user_name, contribution, articles_count, hatena_count, registered_date, registered_date_time)
          VALUES ($name, $contribution, $articlesCount, $hatena, $date, $dateTime);
       """
      .update()
      .apply()
  }
}
