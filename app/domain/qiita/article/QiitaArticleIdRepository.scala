package domain.qiita.article

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaArticleIdRepository {
  def register(qiitaItemId: QiitaItemId)(implicit session: DBSession = AutoSession): Unit

  def retrieveRecently()(implicit session: DBSession = AutoSession): List[QiitaItemId]

  def retrieveNotRegistered()(implicit session: DBSession = AutoSession): List[QiitaItemId]
}
