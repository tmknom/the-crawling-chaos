package infrastructure.qiita.article

import javax.inject.Singleton

import domain.qiita.article.{QiitaArticle, QiitaArticleRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleRepository extends QiitaArticleRepository {
  override def register(qiitaArticle: QiitaArticle)(implicit session: DBSession = AutoSession): Unit = {
    val itemId         = qiitaArticle.itemId.value
    val title          = qiitaArticle.title.value
    val url            = qiitaArticle.url.value
    val postedUserName = qiitaArticle.postedUserName.value
    val postedDateTime = qiitaArticle.postedDateTime.value

    sql"""
          INSERT INTO qiita_articles (item_id, title, url, posted_user_name, posted_date_time)
          VALUES ($itemId, $title, $url, $postedUserName, $postedDateTime);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
