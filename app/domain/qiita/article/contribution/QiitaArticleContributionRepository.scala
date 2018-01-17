package domain.qiita.article.contribution

import domain.qiita.article.QiitaItemId
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaArticleContributionRepository {
  def register(qiitaItemId: QiitaItemId, qiitaArticleContribution: QiitaArticleContribution)(implicit session: DBSession = AutoSession): Unit
}
