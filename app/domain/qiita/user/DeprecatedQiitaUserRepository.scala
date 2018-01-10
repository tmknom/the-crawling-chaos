package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait DeprecatedQiitaUserRepository {
  def delete(qiitaUserId: QiitaUserId)(implicit session: DBSession = AutoSession): Unit

  def retrieveRecently()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser]

  def retrieveContributed()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser]

  def retrieveTop1000()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser]

  def retrieveUnavailable()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser]
}
