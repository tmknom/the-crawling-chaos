package scenario.crawler

import javax.inject.{Inject, Singleton}

import application.crawler.user.QiitaUserContributionCrawlerApplication
import domain.qiita.user.{QiitaUser, QiitaUserRepository}

@Singleton
final class QiitaUserContributionCrawlerScenario @Inject()(
    application:         QiitaUserContributionCrawlerApplication,
    qiitaUserRepository: QiitaUserRepository
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
    * 全ユーザのいいね数をクロール
    */
  def crawlAllUser(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveRecently()
    application.crawl(qiitaUsers)
  }
}
