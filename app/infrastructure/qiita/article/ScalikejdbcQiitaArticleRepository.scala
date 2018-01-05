package infrastructure.qiita.article

import javax.inject.Singleton

import domain.qiita.article.{QiitaArticle, QiitaArticleRepository}
import library.datetime.DateTimeProvider
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleRepository extends QiitaArticleRepository {
  override def register(qiitaArticle: QiitaArticle)(implicit session: DBSession = AutoSession): Unit = {
    val itemId         = qiitaArticle.itemId.value
    val title          = qiitaArticle.title.value
    val url            = qiitaArticle.url.value
    val qiitaUserId    = 1 // TODO 正しい値を入れること！
    val postedDateTime = DateTimeProvider.nowJST() // TODO 正しい値を入れること！

    sql"""
          INSERT INTO qiita_articles (item_id, title, url, qiita_user_id, posted_date_time)
          VALUES ($itemId, $title, $url, $qiitaUserId, $postedDateTime);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
