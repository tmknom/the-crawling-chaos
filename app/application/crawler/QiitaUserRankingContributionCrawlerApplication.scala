package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.contribution.{QiitaUserContributionGateway, QiitaUserContributionRepository}
import domain.qiita.user.ranking.{QiitaUserRanking, QiitaUserRankingRepository}
import play.api.Logger

import scala.collection.mutable

@Singleton
final class QiitaUserRankingContributionCrawlerApplication @Inject()(
    gateway:                    QiitaUserContributionGateway,
    repository:                 QiitaUserContributionRepository,
    qiitaUserRankingRepository: QiitaUserRankingRepository
) {

  private val SLEEP_TIME_MILLISECONDS = 250.toLong

  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errorQiitaUserNames = mutable.ListBuffer.empty[String]

  def crawl(): Unit = {
    val qiitaUserRankings: Seq[QiitaUserRanking] = qiitaUserRankingRepository.retrieveAll()
    qiitaUserRankings.zipWithIndex.foreach {
      case (qiitaUserRanking, index) =>
        quietlyCrawlOneUser(qiitaUserRanking, index)
        TimeUnit.MILLISECONDS.sleep(SLEEP_TIME_MILLISECONDS)
    }
    Logger.info(s"crawl error ${errorQiitaUserNames.size.toString} users ( ${errorQiitaUserNames.mkString(",")} )")
  }

  private def quietlyCrawlOneUser(qiitaUserRanking: QiitaUserRanking, index: Int): Unit = {
    try {
      crawlOneUser(qiitaUserRanking, index)
    } catch {
      // 処理を止めてほしくないのでログ吐いて握りつぶす
      case e: Exception => {
        errorQiitaUserNames += qiitaUserRanking.name.value
        Logger.warn(s"crawl error ${index.toString} ${qiitaUserRanking.name.value}.", e)
      }
    }
  }

  private def crawlOneUser(qiitaUserRanking: QiitaUserRanking, index: Int): Unit = {
    val qiitaUserContribution = gateway.fetch(qiitaUserRanking.name)
    repository.register(qiitaUserRanking.qiitaUserId, qiitaUserContribution)
    Logger.info(s"crawled ${index + 1} : ${qiitaUserRanking.name} : $qiitaUserContribution")
  }
}
