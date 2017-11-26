package domain.qiita.userranking

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserRankingRepository {
  def register(qiitaUserRanking: QiitaUserRanking)(implicit session: DBSession = AutoSession): Unit
}
