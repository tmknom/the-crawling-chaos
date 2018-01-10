package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserNameRepository {
  def register(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Unit

  def delete(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Unit

  def retrieveRecently()(implicit session: DBSession = AutoSession): List[QiitaUserName]

  def retrieveUnavailable()(implicit session: DBSession = AutoSession): List[QiitaUserName]
}
