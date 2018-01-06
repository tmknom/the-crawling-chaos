package domain.qiita.article.contribution

import domain.qiita.article.QiitaArticleId
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaArticleContributionRepository {
  def register(qiitaArticleId: QiitaArticleId, qiitaArticleContribution: QiitaArticleContribution)(implicit session: DBSession = AutoSession): Unit
}
