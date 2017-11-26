package infrastructure.qiita.userranking

import domain.qiita.userranking.{QiitaUserRanking, QiitaUserRankingRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
final class ScalikejdbcQiitaUserRankingRepository extends QiitaUserRankingRepository {
  override def register(qiitaUserRanking: QiitaUserRanking)(implicit session: DBSession = AutoSession): Unit = {
    val userName     = qiitaUserRanking.name.value
    val contribution = qiitaUserRanking.contribution.value
    sql"INSERT INTO qiita_user_rankings (user_name, contribution) VALUES ($userName, $contribution);".update.apply()

    () // 明示的に Unit を返す
  }
}
