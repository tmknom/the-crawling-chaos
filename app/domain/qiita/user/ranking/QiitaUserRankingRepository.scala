package domain.qiita.user.ranking

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserRankingRepository {
  def register(qiitaUserRanking: QiitaUserRanking)(implicit session: DBSession = AutoSession): Unit

  def retrieveAll()(implicit session: DBSession = AutoSession): Seq[QiitaUserRanking]
}
