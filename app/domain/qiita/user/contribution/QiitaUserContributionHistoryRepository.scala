package domain.qiita.user.contribution

import domain.qiita.user.event.QiitaUserContributionCrawledEvent
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserContributionHistoryRepository {
  def register(event: QiitaUserContributionCrawledEvent)(implicit session: DBSession = AutoSession): Int
}
