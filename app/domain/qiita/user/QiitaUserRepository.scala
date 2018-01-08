package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserRepository {
  def register(qiitaUserName: QiitaUserName, registeredDateTime: RegisteredDateTime)(implicit session: DBSession = AutoSession): Unit

  def delete(qiitaUserId: QiitaUserId)(implicit session: DBSession = AutoSession): Unit

  def retrieveRecently()(implicit session: DBSession = AutoSession): Seq[QiitaUser]

  def retrieveContributed()(implicit session: DBSession = AutoSession): Seq[QiitaUser]

  def retrieveTop1000()(implicit session: DBSession = AutoSession): Seq[QiitaUser]

  def retrieveUnavailable()(implicit session: DBSession = AutoSession): Seq[QiitaUser]
}
