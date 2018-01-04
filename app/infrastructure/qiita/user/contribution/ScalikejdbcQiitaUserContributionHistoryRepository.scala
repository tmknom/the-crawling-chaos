package infrastructure.qiita.user.contribution

import javax.inject.Singleton

import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionHistoryRepository}
import domain.qiita.user.{QiitaUserId, RegisteredDateTime}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserContributionHistoryRepository extends QiitaUserContributionHistoryRepository {

  // scalastyle:off
  def register(qiitaUserId: QiitaUserId, qiitaUserContribution: QiitaUserContribution, registeredDateTime: RegisteredDateTime)(implicit session: DBSession =
                                                                                                                                 AutoSession): Int = {
    val id             = qiitaUserId.value
    val contribution   = qiitaUserContribution.value
    val registeredDate = registeredDateTime.toLocalDate
    val registered     = registeredDateTime.value
    sql"INSERT INTO qiita_user_contribution_histories (qiita_user_id, contribution, registered_date, registered_date_time) VALUES ($id, $contribution, $registeredDate, $registered);".update
      .apply()
  }
}
