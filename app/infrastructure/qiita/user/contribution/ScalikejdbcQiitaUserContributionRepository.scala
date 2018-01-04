package infrastructure.qiita.user.contribution

import javax.inject.Singleton

import domain.qiita.user.QiitaUserId
import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionRepository, UpdatedDateTime}
import domain.qiita.user.summary.QiitaUserSummary
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserContributionRepository extends QiitaUserContributionRepository {

  // scalastyle:off
  def register(qiitaUserSummary: QiitaUserSummary, updatedDateTime: UpdatedDateTime)(implicit session: DBSession = AutoSession): Int = {
    val id            = qiitaUserSummary.id.value
    val contribution  = qiitaUserSummary.contribution.value
    val articlesCount = qiitaUserSummary.articlesCount.value
    val updated       = updatedDateTime.value
    sql"INSERT INTO qiita_user_contributions (qiita_user_id, contribution, articles_count, updated_date_time) VALUES ($id, $contribution, $articlesCount, $updated) ON DUPLICATE KEY UPDATE qiita_user_id = VALUES(qiita_user_id), contribution = VALUES(contribution), articles_count = VALUES(articles_count), updated_date_time = VALUES(updated_date_time);".update
      .apply()
  }

  def retrieve(qiitaUserId: QiitaUserId)(implicit session: DBSession = AutoSession): QiitaUserContribution = {
    sql"SELECT qiita_user_id, contribution FROM qiita_user_contributions;".map { rs =>
      QiitaUserContribution(rs.int("contribution"))
    }.single().apply().getOrElse(throw new RuntimeException(s"Not Found : $qiitaUserId"))
  }
}
