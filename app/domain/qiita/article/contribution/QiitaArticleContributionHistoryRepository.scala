package domain.qiita.article.contribution

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaArticleContributionHistoryRepository {
  def register(event: QiitaArticleContributionCrawledEvent)(implicit session: DBSession = AutoSession): Unit
}
