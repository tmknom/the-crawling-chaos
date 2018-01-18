package infrastructure.qiita.article.contribution

import javax.inject.Singleton

import domain.qiita.article.contribution.{QiitaArticleContributionCrawledEvent, QiitaArticleContributionHistoryRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleContributionHistoryRepository extends QiitaArticleContributionHistoryRepository {
  override def register(event: QiitaArticleContributionCrawledEvent)(implicit session: DBSession = AutoSession): Unit = {
    val itemId        = event.qiitaItemId.value
    val likesCount    = event.qiitaArticleContribution.likesCount.value
    val commentsCount = event.qiitaArticleContribution.commentsCount.value
    val hatenaCount   = event.qiitaArticleContribution.hatenaCount.value
    val facebookCount = event.qiitaArticleContribution.facebookCount.value
    val pocketCount   = event.qiitaArticleContribution.pocketCount.value
    val dateTime      = event.crawledDateTime.value
    val date          = event.crawledDateTime.toLocalDate

    sql"""
          INSERT INTO qiita_article_contribution_histories
          (item_id, likes_count, comments_count, hatena_count, facebook_count, pocket_count, registered_date, registered_date_time)
          VALUES ($itemId, $likesCount, $commentsCount, $hatenaCount, $facebookCount, $pocketCount, $date, $dateTime);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
