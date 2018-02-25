package domain.qiita.user.contribution

import domain.qiita.article.contribution.HatenaCount
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaUserContributionHistoryRepository {
  def register(event: QiitaUserContributionCrawledEvent, hatenaCount: HatenaCount)(implicit session: DBSession = AutoSession): Int
}
