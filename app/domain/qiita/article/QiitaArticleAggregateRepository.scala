package domain.qiita.article

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaArticleAggregateRepository {
  def retrieveContribution()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate]

  def retrieveCommentsCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate]

  def retrieveHatenaCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate]

  def retrieveFacebookCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate]

  def retrievePocketCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate]
}
