package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserRepository {
  def retrieveTop100()(implicit session: DBSession = AutoSession): List[QiitaUser]

  def countContribution()(implicit session: DBSession = AutoSession): Long

  def retrieveContribution(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser]

  def countArticlesCount()(implicit session: DBSession = AutoSession): Long

  def retrieveArticlesCount(limit: Int, offset: Int)(implicit session: DBSession = AutoSession): List[QiitaUser]
}
