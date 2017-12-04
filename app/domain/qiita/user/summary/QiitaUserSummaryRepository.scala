package domain.qiita.user.summary

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserSummaryRepository {
  def retrieveTop200()(implicit session: DBSession = AutoSession): List[QiitaUserSummary]
}
