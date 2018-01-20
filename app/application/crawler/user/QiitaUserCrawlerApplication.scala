package application.crawler.user

import javax.inject.{Inject, Singleton}

import domain.crawler.{Progress, Sleeper}
import domain.qiita.user._
import domain.qiita.user.contribution.{QiitaUserContributionHistoryRepository, QiitaUserContributionRepository}
import library.scalaj.ScalajHttpException
import play.api.Logger
import scalikejdbc.DB

@Singleton
final class QiitaUserCrawlerApplication @Inject()(
    gateway:                                QiitaUserInternalApiGateway,
    qiitaRawInternalUserJsonRepository:     QiitaRawInternalUserJsonRepository,
    qiitaUserContributionRepository:        QiitaUserContributionRepository,
    qiitaUserContributionHistoryRepository: QiitaUserContributionHistoryRepository,
    qiitaUserNameRepository:                QiitaUserNameRepository
) {

  def crawlTopUser(): Unit = {
    val qiitaUserNames = qiitaUserNameRepository.retrieveTopUser()
    crawlUsers(qiitaUserNames)
  }

  def crawlContributedUser(): Unit = {
    val qiitaUserNames = qiitaUserNameRepository.retrieveContributedUser()
    crawlUsers(qiitaUserNames)
  }

  private def crawlUsers(qiitaUserNames: List[QiitaUserName]): Unit = {
    val itemsCount = qiitaUserNames.size
    qiitaUserNames.zipWithIndex.foreach {
      case (qiitaUserName, index) =>
        val progress = Progress.calculate(index, itemsCount)
        quietlyCrawlOneUser(qiitaUserName, progress)
    }
  }

  private def quietlyCrawlOneUser(qiitaUserName: QiitaUserName, progress: String): Unit = {
    try {
      crawlOneUser(qiitaUserName)
      Logger.info(s"${this.getClass.getSimpleName} crawled ${qiitaUserName.value} $progress")
    } catch {
      // 処理を止めてほしくないのでログ吐いて握りつぶす
      case e: ScalajHttpException =>
        Logger.warn(s"${this.getClass.getSimpleName} crawl error ${qiitaUserName.value} $progress because ${e.message}")
    } finally {
      Sleeper.sleep()
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
