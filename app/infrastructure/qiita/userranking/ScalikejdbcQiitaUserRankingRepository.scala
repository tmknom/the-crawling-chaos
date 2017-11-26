package infrastructure.qiita.userranking

import domain.qiita.userranking.{QiitaUserRanking, QiitaUserRankingRepository}
import scalikejdbc._

final class ScalikejdbcQiitaUserRankingRepository extends QiitaUserRankingRepository {
  override def register(qiitaUserRanking: QiitaUserRanking)(implicit session: DBSession = AutoSession): Unit = {
    val userName     = qiitaUserRanking.name.value
    val contribution = qiitaUserRanking.contribution.value
    sql"INSERT INTO qiita_user_rankings (user_name, contribution) VALUES ($userName, $contribution);".update.apply()
  }
}
