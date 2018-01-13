package application.crawler.user

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

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
    qiitaUserRepository:                    QiitaUserRepository
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    val qiitaUsers     = qiitaUserRepository.retrieveTop1000()
    val qiitaUserNames = qiitaUsers.map(_.profile.name)
    crawlUsers(qiitaUserNames)
  }

  private def crawlUsers(qiitaUserNames: List[QiitaUserName]): Unit = {
    val itemsCount = qiitaUserNames.size
    qiitaUserNames.zipWithIndex.foreach {
      case (qiitaUserName, index) =>
        val progress = calculateProgress(index, itemsCount)
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
      TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
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

  private def calculateProgress(index: Int, itemsCount: Int): String = {
    val progress = ((index + 1) / itemsCount.toDouble) * 100.0
    s"(${index + 1} / $itemsCount = $progress%)"
  }
}
