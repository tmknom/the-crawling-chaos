package infrastructure.qiita.article.contribution

import javax.inject.Singleton

import domain.qiita.article.QiitaArticleId
import domain.qiita.article.contribution._
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleContributionRepository extends QiitaArticleContributionRepository {
  override def register(qiitaArticleId: QiitaArticleId, qiitaArticleContribution: QiitaArticleContribution)(implicit session: DBSession = AutoSession): Unit = {
    val id            = qiitaArticleId.value
    val likesCount    = qiitaArticleContribution.likesCount.value
    val commentsCount = qiitaArticleContribution.commentsCount.value
    val hatenaCount   = qiitaArticleContribution.hatenaCount.value
    val facebookCount = qiitaArticleContribution.facebookCount.value
    val pocketCount   = qiitaArticleContribution.pocketCount.value

    sql"""
          INSERT INTO qiita_article_contributions (qiita_article_id, likes_count, comments_count, hatena_count, facebook_count, pocket_count)
          VALUES ($id, $likesCount, $commentsCount, $hatenaCount, $facebookCount, $pocketCount);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
