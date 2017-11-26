package domain.qiita.user.contribution

import domain.qiita.user.QiitaUserId
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserContributionRepository {
  def register(qiitaUserId: QiitaUserId, qiitaUserContribution: QiitaUserContribution)(implicit session: DBSession = AutoSession): Unit
}
