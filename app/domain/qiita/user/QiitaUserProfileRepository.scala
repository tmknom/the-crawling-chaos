package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserProfileRepository {
  def register(qiitaUserProfile: QiitaUserProfile)(implicit session: DBSession = AutoSession): Unit

  def retrieve(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Option[QiitaUserProfile]
}
