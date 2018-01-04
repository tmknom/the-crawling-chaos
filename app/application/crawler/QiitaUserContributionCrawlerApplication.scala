package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.contribution.{QiitaUserContributionGateway, QiitaUserContributionHistoryRepository, QiitaUserContributionRepository, UpdatedDateTime}
import domain.qiita.user.{QiitaUser, QiitaUserRepository, RegisteredDateTime}
import play.api.Logger

import scala.collection.mutable

@Singleton
final class QiitaUserContributionCrawlerApplication @Inject()(
    gateway:             QiitaUserContributionGateway,
    repository:          QiitaUserContributionRepository,
    historyRepository:   QiitaUserContributionHistoryRepository,
    qiitaUserRepository: QiitaUserRepository
) {

  private val SleepTimeMilliseconds = 100.toLong

  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errorQiitaUserNames = mutable.ListBuffer.empty[String]

  def crawl(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveAll()
    val qiitaUsersSize = qiitaUsers.zipWithIndex.size

    qiitaUsers.zipWithIndex.foreach {
      case (qiitaUser, index) =>
        quietlyCrawlOneUser(qiitaUser, index)

        log(qiitaUser, index, qiitaUsersSize)
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
    Logger.info(s"crawl error ${errorQiitaUserNames.size.toString} users ( ${errorQiitaUserNames.mkString(",")} )")
  }

  private def quietlyCrawlOneUser(qiitaUser: QiitaUser, index: Int): Unit = {
    try {
      crawlOneUser(qiitaUser)
    } catch {
      // 処理を止めてほしくないのでログ吐いて握りつぶす
      case e: Exception => {
        errorQiitaUserNames += qiitaUser.name.value
        Logger.warn(s"crawl error ${index.toString} ${qiitaUser.name.value}.", e)
      }
    }
  }

  private def crawlOneUser(qiitaUser: QiitaUser): Unit = {
    val qiitaUserSummary = gateway.fetch(qiitaUser)

    val updatedDateTime = UpdatedDateTime.now()
    repository.register(qiitaUserSummary, updatedDateTime)

    val registeredDateTime = RegisteredDateTime(updatedDateTime.value)
    historyRepository.register(qiitaUserSummary, registeredDateTime)
  }

  private def log(qiitaUser: QiitaUser, index: Int, qiitaUsersSize: Int) = {
    val progress = (index + 1) / qiitaUsersSize
    Logger.info(s"crawled ${qiitaUser.name.value} : ${index + 1} / $qiitaUsersSize ($progress%)")
  }
}
