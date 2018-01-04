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
    * 全ユーザのいいね数をクロール
    */
  def crawlAllUser(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveAll()
    qiitaUserContributionCrawlerApplication.crawl(qiitaUsers)
  }
}
