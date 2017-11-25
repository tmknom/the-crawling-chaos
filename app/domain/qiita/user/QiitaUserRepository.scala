package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

trait QiitaUserRepository {
  def register(qiitaUser: QiitaUser)(implicit session: DBSession = AutoSession): Unit
}
