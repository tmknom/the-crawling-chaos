package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait DeprecatedQiitaUserRepository {
  def retrieveContributed()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser]

  def retrieveTop1000()(implicit session: DBSession = AutoSession): Seq[DeprecatedQiitaUser]
}
