package infrastructure.qiita.article

import javax.inject.Singleton

import domain.qiita.article._
import domain.qiita.user.QiitaUserName
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

  override def retrieve(qiitaItemId: QiitaItemId)(implicit session: DBSession = AutoSession): QiitaArticle = {
    val itemId = qiitaItemId.value

    val optionValue =
      sql"""
          SELECT * FROM qiita_articles
          WHERE item_id = $itemId;
       """
        .map(toQiitaArticle)
        .single()
        .apply()

    optionValue match {
      case Some(v) => v
      case None    => throw new RuntimeException(s"Not exists QiitaItemId: $itemId")
    }
  }

  private def toQiitaArticle(rs: WrappedResultSet): QiitaArticle = {
    QiitaArticle(
      QiitaArticleId(rs.int("id")),
      QiitaItemId(rs.string("item_id")),
      QiitaArticleTitle(rs.string("title")),
      QiitaArticleUrl(rs.string("url")),
      QiitaArticlePostedDateTime(rs.zonedDateTime("posted_date_time")),
      QiitaUserName(rs.string("posted_user_name"))
    )
  }
}
