package infrastructure.qiita.user.contribution

import javax.inject.Singleton

import domain.qiita.user.QiitaUserId
import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserContributionRepository extends QiitaUserContributionRepository {

  def register(qiitaUserId: QiitaUserId, qiitaUserContribution: QiitaUserContribution)(implicit session: DBSession = AutoSession): Int = {
    val id           = qiitaUserId.value
    val contribution = qiitaUserContribution.value
    sql"INSERT INTO qiita_user_contributions (qiita_user_id, contribution) VALUES ($id, $contribution);".update.apply()
  }

  def retrieve(qiitaUserId: QiitaUserId)(implicit session: DBSession = AutoSession): QiitaUserContribution = {
    sql"SELECT qiita_user_id, contribution FROM qiita_user_contributions;".map { rs =>
      QiitaUserContribution(rs.int("contribution"))
    }.single().apply().getOrElse(throw new RuntimeException(s"Not Found : $qiitaUserId"))
  }
}
