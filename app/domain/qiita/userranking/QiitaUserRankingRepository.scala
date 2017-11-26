package domain.qiita.userranking

import scalikejdbc.{AutoSession, DBSession}

trait QiitaUserRankingRepository {
  def register(qiitaUserRanking: QiitaUserRanking)(implicit session: DBSession = AutoSession): Unit
}
