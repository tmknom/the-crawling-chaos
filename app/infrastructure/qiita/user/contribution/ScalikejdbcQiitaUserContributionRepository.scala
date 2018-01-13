package infrastructure.qiita.user.contribution

import javax.inject.Singleton

import domain.qiita.user.contribution.{QiitaUserContributionCrawledEvent, QiitaUserContributionRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserContributionRepository extends QiitaUserContributionRepository {

  def register(event: QiitaUserContributionCrawledEvent)(implicit session: DBSession = AutoSession): Int = {
    val name          = event.qiitaUserName.value
    val contribution  = event.qiitaUserContribution.contribution.value
    val articlesCount = event.qiitaUserContribution.articlesCount.value
    val updated       = event.crawledDateTime.value

    sql"""
          INSERT INTO qiita_user_contributions (user_name, contribution, articles_count, updated_date_time)
          VALUES ($name, $contribution, $articlesCount, $updated)
          ON DUPLICATE KEY UPDATE
          contribution = VALUES(contribution),
          articles_count = VALUES(articles_count),
          updated_date_time = VALUES(updated_date_time);
       """
      .update()
      .apply()
  }
}
