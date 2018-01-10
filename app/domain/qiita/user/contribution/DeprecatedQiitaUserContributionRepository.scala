package domain.qiita.user.contribution

import domain.qiita.user.QiitaUserId
import domain.qiita.user.summary.QiitaUserSummary
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait DeprecatedQiitaUserContributionRepository {
  def register(qiitaUserSummary: QiitaUserSummary, updatedDateTime: UpdatedDateTime)(implicit session: DBSession = AutoSession): Int

  def retrieve(qiitaUserId: QiitaUserId)(implicit session: DBSession = AutoSession): QiitaUserContribution
}
