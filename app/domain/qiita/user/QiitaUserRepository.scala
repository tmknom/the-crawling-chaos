package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserRepository {
  def retrieveTop100()(implicit session: DBSession = AutoSession): List[QiitaUser]

  def retrieveTop1000()(implicit session: DBSession = AutoSession): List[QiitaUser]
}
