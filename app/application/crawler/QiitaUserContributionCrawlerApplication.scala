package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.contribution.{QiitaUserContributionGateway, QiitaUserContributionRepository}
import domain.qiita.user.{QiitaUser, QiitaUserRepository}
import play.api.Logger

import scala.collection.mutable

@Singleton
final class QiitaUserContributionCrawlerApplication @Inject()(
    gateway:             QiitaUserContributionGateway,
    repository:          QiitaUserContributionRepository,
    qiitaUserRepository: QiitaUserRepository
) {

  private val SLEEP_TIME_MILLISECONDS = 250.toLong

  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errorQiitaUserNames = mutable.ListBuffer.empty[String]

  def crawl(): Unit = {
    val qiitaUsers: Seq[QiitaUser] = qiitaUserRepository.retrieveAll()
    qiitaUsers.zipWithIndex.foreach {
      case (qiitaUser, index) =>
        quietlyCrawlOneUser(qiitaUser, index)
        TimeUnit.MILLISECONDS.sleep(SLEEP_TIME_MILLISECONDS)
    }
    Logger.info(s"crawl error ${errorQiitaUserNames.size.toString} users ( ${errorQiitaUserNames.mkString(",")} )")
  }

  private def quietlyCrawlOneUser(qiitaUser: QiitaUser, index: Int): Unit = {
    try {
      crawlOneUser(qiitaUser, index)
    } catch {
      // 処理を止めてほしくないのでログ吐いて握りつぶす
      case e: Exception => {
        errorQiitaUserNames += qiitaUser.name.value
        Logger.warn(s"crawl error ${index.toString} ${qiitaUser.name.value}.", e)
      }
    }
  }

  private def crawlOneUser(qiitaUser: QiitaUser, index: Int): Unit = {
    val qiitaUserContribution = gateway.fetch(qiitaUser.name)
    repository.register(qiitaUser.id, qiitaUserContribution)
    Logger.info(s"crawled ${index + 1} : ${qiitaUser.name} : $qiitaUserContribution")
  }
}
