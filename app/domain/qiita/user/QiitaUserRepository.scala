package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserRepository {
  def register(qiitaUser: QiitaUser)(implicit session: DBSession = AutoSession): Unit

  def retrieve(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Option[QiitaUser]
}
