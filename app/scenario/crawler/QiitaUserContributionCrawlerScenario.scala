package scenario.crawler

import javax.inject.{Inject, Singleton}

import application.crawler.user.DeprecatedQiitaUserContributionCrawlerApplication
import domain.qiita.user.{DeprecatedQiitaUser, DeprecatedQiitaUserRepository}

@Singleton
final class QiitaUserContributionCrawlerScenario @Inject()(
    application:         DeprecatedQiitaUserContributionCrawlerApplication,
    qiitaUserRepository: DeprecatedQiitaUserRepository
) {

  /**
    * いいね数トップ1000ユーザのいいね数をクロール
    */
  def crawlTop1000User(): Unit = {
    val qiitaUsers: Seq[DeprecatedQiitaUser] = qiitaUserRepository.retrieveTop1000()
    application.crawl(qiitaUsers)
  }

  /**
    * いいねを獲得したことのあるユーザのいいね数をクロール
    */
  def crawlContributedUser(): Unit = {
    val qiitaUsers: Seq[DeprecatedQiitaUser] = qiitaUserRepository.retrieveContributed()
    application.crawl(qiitaUsers)
  }

  /**
    * 最近登録されたユーザのいいね数をクロール
    */
  def crawlRecentlyUser(): Unit = {
    val qiitaUsers: Seq[DeprecatedQiitaUser] = qiitaUserRepository.retrieveRecently()
    application.crawl(qiitaUsers)
  }
}
