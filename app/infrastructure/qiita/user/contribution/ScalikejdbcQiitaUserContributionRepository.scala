package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserId
import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
final class ScalikejdbcQiitaUserContributionRepository extends QiitaUserContributionRepository {

  def register(qiitaUserId: QiitaUserId, qiitaUserContribution: QiitaUserContribution)(implicit session: DBSession = AutoSession): Unit = {
    val id           = qiitaUserId.value
    val contribution = qiitaUserContribution.value
    sql"INSERT INTO qiita_user_contributions (qiita_user_id, contribution) VALUES ($id, $contribution);".update.apply()

    () // 明示的に Unit を返す
  }
}
