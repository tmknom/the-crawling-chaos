package infrastructure.qiita.userranking

import domain.qiita.user.QiitaUserId
import domain.qiita.userranking._
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
final class ScalikejdbcQiitaUserRankingRepository extends QiitaUserRankingRepository {
  override def register(qiitaUserRanking: QiitaUserRanking)(implicit session: DBSession = AutoSession): Unit = {
    val userName     = qiitaUserRanking.name.value
    val contribution = qiitaUserRanking.contribution.value
    sql"INSERT INTO qiita_user_rankings (user_name, contribution) VALUES ($userName, $contribution);".update.apply()

    () // 明示的に Unit を返す
  }

  def retrieveAll()(implicit session: DBSession = AutoSession): Seq[QiitaUserRanking] = {
    sql"SELECT qu.id, qur.user_name, qur.contribution FROM qiita_user_rankings AS qur INNER JOIN qiita_users AS qu ON qur.user_name = qu.user_name ORDER BY qur.contribution DESC LIMIT 10;".map {
      rs =>
        QiitaUserRanking(QiitaUserId(rs.int("id")), QiitaUserRankingName(rs.string("user_name")), QiitaUserRankingContribution(rs.int("contribution")))
    }.list().apply()
  }
}
