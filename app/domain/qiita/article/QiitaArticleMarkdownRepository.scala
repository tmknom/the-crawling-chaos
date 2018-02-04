package domain.qiita.article

import domain.qiita.article.markdown.MarkdownCrawledEvent
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaArticleMarkdownRepository {
  def register(event: MarkdownCrawledEvent)(implicit session: DBSession = AutoSession): Unit
}
