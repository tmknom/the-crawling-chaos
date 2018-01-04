package domain.qiita.user.contribution

import domain.qiita.user.QiitaUserId
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserContributionRepository {
  def register(qiitaUserId: QiitaUserId, qiitaUserContribution: QiitaUserContribution, updatedDateTime: UpdatedDateTime)(implicit session: DBSession =
                                                                                                                           AutoSession): Int

  def retrieve(qiitaUserId: QiitaUserId)(implicit session: DBSession = AutoSession): QiitaUserContribution
}
