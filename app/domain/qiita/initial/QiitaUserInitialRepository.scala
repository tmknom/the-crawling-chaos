package domain.qiita.initial

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserInitialRepository {
  def register(qiitaUserInitial: QiitaUserInitial)(implicit session: DBSession = AutoSession): Unit

  def retrieveAll()(implicit session: DBSession = AutoSession): Seq[QiitaUserInitial]
}
