package domain.qiita

import scalikejdbc.{AutoSession, DBSession}

trait QiitaUserInitialRepository {
  def register(qiitaUserInitial: QiitaUserInitial)(implicit session: DBSession = AutoSession): Unit
}
