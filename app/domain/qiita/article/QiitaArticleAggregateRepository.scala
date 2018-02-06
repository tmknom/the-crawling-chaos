package domain.qiita.article

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaArticleAggregateRepository {
  def retrieveContribution()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate]
}
