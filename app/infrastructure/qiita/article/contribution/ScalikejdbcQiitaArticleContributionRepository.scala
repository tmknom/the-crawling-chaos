package infrastructure.qiita.article.contribution

import javax.inject.Singleton

import domain.qiita.article.contribution.{QiitaArticleContributionCrawledEvent, QiitaArticleContributionRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleContributionRepository extends QiitaArticleContributionRepository {
  override def register(event: QiitaArticleContributionCrawledEvent)(implicit session: DBSession = AutoSession): Unit = {
    val itemId        = event.qiitaItemId.value
    val likesCount    = event.qiitaArticleContribution.likesCount.value
    val commentsCount = event.qiitaArticleContribution.commentsCount.value
    val hatenaCount   = event.qiitaArticleContribution.hatenaCount.value
    val facebookCount = event.qiitaArticleContribution.facebookCount.value
    val pocketCount   = event.qiitaArticleContribution.pocketCount.value
    val updated       = event.crawledDateTime.value

    sql"""
          INSERT INTO qiita_article_contributions
          (item_id, likes_count, comments_count, hatena_count, facebook_count, pocket_count, updated_date_time)
          VALUES ($itemId, $likesCount, $commentsCount, $hatenaCount, $facebookCount, $pocketCount, $updated)
          ON DUPLICATE KEY UPDATE
          likes_count = VALUES(likes_count),
          comments_count = VALUES(comments_count),
          hatena_count = VALUES(hatena_count),
          facebook_count = VALUES(facebook_count),
          pocket_count = VALUES(pocket_count),
          updated_date_time = VALUES(updated_date_time);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
