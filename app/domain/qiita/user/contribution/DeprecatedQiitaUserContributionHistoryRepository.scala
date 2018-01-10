package domain.qiita.user.contribution

import domain.qiita.user.RegisteredDateTime
import domain.qiita.user.summary.QiitaUserSummary
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait DeprecatedQiitaUserContributionHistoryRepository {
  def register(qiitaUserSummary: QiitaUserSummary, registeredDateTime: RegisteredDateTime)(implicit session: DBSession = AutoSession): Int
}
