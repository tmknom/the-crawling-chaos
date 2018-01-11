package application.crawler.user

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.contribution.{
  DeprecatedQiitaUserContributionHistoryRepository,
  DeprecatedQiitaUserContributionRepository,
  DeprecatedQiitaUserInternalApiGateway,
  UpdatedDateTime
}
import domain.qiita.user.{DeprecatedQiitaUser, RegisteredDateTime}
import play.api.Logger
import scalikejdbc.DB

import scala.collection.mutable

@Singleton
final class DeprecatedQiitaUserContributionCrawlerApplication @Inject()(
    gateway:           DeprecatedQiitaUserInternalApiGateway,
    repository:        DeprecatedQiitaUserContributionRepository,
    historyRepository: DeprecatedQiitaUserContributionHistoryRepository
) {

  private val SleepTimeMilliseconds = 100.toLong

  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errorQiitaUserNames = mutable.ListBuffer.empty[String]

  def crawl(qiitaUsers: Seq[DeprecatedQiitaUser]): Unit = {
    val qiitaUsersSize = qiitaUsers.zipWithIndex.size

    qiitaUsers.zipWithIndex.foreach {
      case (qiitaUser, index) =>
        quietlyCrawlOneUser(qiitaUser, index)

        log(qiitaUser, index, qiitaUsersSize)
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
    Logger.info(s"crawl error ${errorQiitaUserNames.size.toString} users ( ${errorQiitaUserNames.mkString(",")} )")
  }

  private def quietlyCrawlOneUser(qiitaUser: DeprecatedQiitaUser, index: Int): Unit = {
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

  private def crawlOneUser(qiitaUser: DeprecatedQiitaUser): Unit = {
    val qiitaUserSummary = gateway.fetch(qiitaUser)

    val updatedDateTime    = UpdatedDateTime.now()
    val registeredDateTime = RegisteredDateTime(updatedDateTime.value)

    DB.localTx { implicit session =>
      repository.register(qiitaUserSummary, updatedDateTime)
      historyRepository.register(qiitaUserSummary, registeredDateTime)
    }
  }

  private def log(qiitaUser: DeprecatedQiitaUser, index: Int, qiitaUsersSize: Int) = {
    val progress = ((index + 1) / qiitaUsersSize.toDouble) * 100.0
    Logger.info(s"crawled ${qiitaUser.name.value} : ${index + 1} / $qiitaUsersSize ($progress%)")
  }
}