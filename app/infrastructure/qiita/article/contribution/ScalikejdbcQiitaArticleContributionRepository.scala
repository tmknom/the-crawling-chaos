package infrastructure.qiita.article.contribution

import javax.inject.Singleton

import domain.qiita.article.QiitaItemId
import domain.qiita.article.contribution.{QiitaArticleContribution, QiitaArticleContributionRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleContributionRepository extends QiitaArticleContributionRepository {
  override def register(qiitaItemId: QiitaItemId, qiitaArticleContribution: QiitaArticleContribution)(implicit session: DBSession = AutoSession): Unit = {
    val itemId        = qiitaItemId.value
    val likesCount    = qiitaArticleContribution.likesCount.value
    val commentsCount = qiitaArticleContribution.commentsCount.value
    val hatenaCount   = qiitaArticleContribution.hatenaCount.value
    val facebookCount = qiitaArticleContribution.facebookCount.value
    val pocketCount   = qiitaArticleContribution.pocketCount.value

    sql"""
          INSERT INTO qiita_article_contributions (item_id, likes_count, comments_count, hatena_count, facebook_count, pocket_count)
          VALUES ($itemId, $likesCount, $commentsCount, $hatenaCount, $facebookCount, $pocketCount);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
