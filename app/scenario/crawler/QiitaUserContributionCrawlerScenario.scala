package scenario.crawler

import javax.inject.{Inject, Singleton}

import application.crawler.QiitaUserContributionCrawlerApplication
import domain.qiita.user.{QiitaUser, QiitaUserRepository}

@Singleton
final class QiitaUserContributionCrawlerScenario @Inject()(
    qiitaUserContributionCrawlerApplication: QiitaUserContributionCrawlerApplication,
    qiitaUserRepository:                     QiitaUserRepository
) {

  /**
    * いいね数トップ1000ユーザのいいね数をクロール
    */
  def crawlTop1000User(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveTop1000()
    qiitaUserContributionCrawlerApplication.crawl(qiitaUsers)
  }

  /**
    * いいねを獲得したことのあるユーザのいいね数をクロール
    */
  def crawlContributedUser(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveContributed()
    qiitaUserContributionCrawlerApplication.crawl(qiitaUsers)
  }

  /**
    * 全ユーザのいいね数をクロール
    */
  def crawlAllUser(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveAll()
    qiitaUserContributionCrawlerApplication.crawl(qiitaUsers)
  }
}
