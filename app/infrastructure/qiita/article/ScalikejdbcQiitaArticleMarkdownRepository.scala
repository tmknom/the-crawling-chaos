package infrastructure.qiita.article

import javax.inject.Singleton

import domain.qiita.article._
import domain.qiita.article.markdown.MarkdownCrawledEvent
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleMarkdownRepository extends QiitaArticleMarkdownRepository {
  override def register(event: MarkdownCrawledEvent)(implicit session: DBSession = AutoSession): Unit = {
    val itemId             = event.qiitaItemId.value
    val markdown           = event.markdown.value
    val registeredDateTime = event.crawledDateTime.value

    sql"""
          INSERT INTO qiita_article_markdowns (item_id, markdown, registered_date_time)
          VALUES ($itemId, $markdown, $registeredDateTime);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
