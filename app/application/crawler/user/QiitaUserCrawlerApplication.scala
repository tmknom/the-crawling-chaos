package application.crawler.user

import javax.inject.{Inject, Singleton}

import domain.crawler.{Bulk, QuietlyCrawler}
import domain.qiita.user._
import domain.qiita.user.contribution.{QiitaUserContributionHistoryRepository, QiitaUserContributionRepository}
import scalikejdbc.DB

@Singleton
final class QiitaUserCrawlerApplication @Inject()(
    gateway:                                QiitaUserInternalApiGateway,
    qiitaRawInternalUserJsonRepository:     QiitaRawInternalUserJsonRepository,
    qiitaUserContributionRepository:        QiitaUserContributionRepository,
    qiitaUserContributionHistoryRepository: QiitaUserContributionHistoryRepository,
    qiitaUserNameRepository:                QiitaUserNameRepository
) extends Bulk
    with QuietlyCrawler {

  def crawlTopUser(): Unit = {
    val qiitaUserNames = qiitaUserNameRepository.retrieveTopUser()
    crawlUsers(qiitaUserNames)
  }

  def crawlContributedUser(): Unit = {
    val qiitaUserNames = qiitaUserNameRepository.retrieveContributedUser()
    crawlUsers(qiitaUserNames)
  }

  private def crawlUsers(qiitaUserNames: List[QiitaUserName]): Unit = {
    withLoop[QiitaUserName](qiitaUserNames) { (qiitaUserName, progress) =>
      quietlyCrawlOneUser(qiitaUserName, progress)
    }
  }

  private def quietlyCrawlOneUser(qiitaUserName: QiitaUserName, progress: String): Unit = {
    withQuietly[String](qiitaUserName, progress, errors) { (_) =>
      crawlOneUser(qiitaUserName)
    }
  }

  private def crawlOneUser(qiitaUserName: QiitaUserName): Unit = {
    val rawInternalUserJson = gateway.fetch(qiitaUserName)
    val crawledEvent        = rawInternalUserJson.toCrawledEvent

    DB.localTx { implicit session =>
      qiitaRawInternalUserJsonRepository.register(qiitaUserName, rawInternalUserJson, crawledEvent.crawledDateTime)
      qiitaUserContributionRepository.register(crawledEvent)
      qiitaUserContributionHistoryRepository.register(crawledEvent)
    }
  }
}
