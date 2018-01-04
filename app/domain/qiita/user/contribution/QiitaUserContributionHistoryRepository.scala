package domain.qiita.user.contribution

import domain.qiita.user.{QiitaUserId, RegisteredDateTime}
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserContributionHistoryRepository {
  def register(qiitaUserId: QiitaUserId, qiitaUserContribution: QiitaUserContribution, registeredDateTime: RegisteredDateTime)(implicit session: DBSession =
                                                                                                                                 AutoSession): Int
}
