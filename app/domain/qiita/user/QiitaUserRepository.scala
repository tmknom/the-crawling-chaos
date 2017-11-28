package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserRepository {
  def register(qiitaUser: QiitaUser)(implicit session: DBSession = AutoSession): Unit

  def retrieveAll()(implicit session: DBSession = AutoSession): Seq[QiitaUser]
}
