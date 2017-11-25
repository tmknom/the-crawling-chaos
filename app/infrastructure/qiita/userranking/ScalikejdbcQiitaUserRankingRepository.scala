package infrastructure.qiita.userranking

import domain.qiita.userranking.{QiitaUserRanking, QiitaUserRankingRepository}
import scalikejdbc._

final class ScalikejdbcQiitaUserRankingRepository extends QiitaUserRankingRepository {
  override def register(qiitaUserRanking: QiitaUserRanking)(implicit session: DBSession = AutoSession): Unit = {}
}
