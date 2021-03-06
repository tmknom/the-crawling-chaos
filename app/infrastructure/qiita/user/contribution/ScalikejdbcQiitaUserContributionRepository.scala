package infrastructure.qiita.user.contribution

import javax.inject.Singleton

import domain.qiita.article.contribution.HatenaCount
import domain.qiita.user.contribution.{QiitaUserContributionCrawledEvent, QiitaUserContributionRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserContributionRepository extends QiitaUserContributionRepository {

  def register(event: QiitaUserContributionCrawledEvent, hatenaCount: HatenaCount)(implicit session: DBSession = AutoSession): Int = {
    val name          = event.qiitaUserName.value
    val contribution  = event.qiitaUserContribution.contribution.value
    val articlesCount = event.qiitaUserContribution.articlesCount.value
    val hatena        = hatenaCount.value
    val updated       = event.crawledDateTime.value

    sql"""
          INSERT INTO qiita_user_contributions (user_name, contribution, articles_count, hatena_count, updated_date_time)
          VALUES ($name, $contribution, $articlesCount, $hatena, $updated)
          ON DUPLICATE KEY UPDATE
          contribution = VALUES(contribution),
          articles_count = VALUES(articles_count),
          hatena_count = VALUES(hatena_count),
          updated_date_time = VALUES(updated_date_time);
       """
      .update()
      .apply()
  }
}
