package scenario.crawler

import javax.inject.{Inject, Singleton}

import application.crawler.user.QiitaUserContributionCrawlerApplication
import domain.qiita.user.{DeprecatedQiitaUserRepository, QiitaUser}

@Singleton
final class QiitaUserContributionCrawlerScenario @Inject()(
    application:         QiitaUserContributionCrawlerApplication,
    qiitaUserRepository: DeprecatedQiitaUserRepository
) {

  /**
    * いいね数トップ1000ユーザのいいね数をクロール
    */
  def crawlTop1000User(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveTop1000()
    application.crawl(qiitaUsers)
  }

  /**
    * いいねを獲得したことのあるユーザのいいね数をクロール
    */
  def crawlContributedUser(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveContributed()
    application.crawl(qiitaUsers)
  }

  /**
    * 最近登録されたユーザのいいね数をクロール
    */
  def crawlRecentlyUser(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveRecently()
    application.crawl(qiitaUsers)
  }
}
