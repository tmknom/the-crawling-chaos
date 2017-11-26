package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.contribution.{QiitaUserContributionGateway, QiitaUserContributionRepository}
import domain.qiita.user.ranking.{QiitaUserRanking, QiitaUserRankingRepository}
import play.api.Logger

@Singleton
final class QiitaUserContributionCrawlerApplication @Inject()(
    gateway:                    QiitaUserContributionGateway,
    repository:                 QiitaUserContributionRepository,
    qiitaUserRankingRepository: QiitaUserRankingRepository
) {

  private val SLEEP_TIME_MILLISECONDS = 250.toLong

  def crawl(): Unit = {
    val qiitaUserRankings: Seq[QiitaUserRanking] = qiitaUserRankingRepository.retrieveAll()
    qiitaUserRankings.zipWithIndex.foreach {
      case (qiitaUserRanking, index) =>
        val qiitaUserContribution = gateway.fetch(qiitaUserRanking.name)
        repository.register(qiitaUserRanking.qiitaUserId, qiitaUserContribution)
        Logger.info(s"crawled ${index + 1} : ${qiitaUserRanking.name} : ${qiitaUserContribution.value}")
        TimeUnit.MILLISECONDS.sleep(SLEEP_TIME_MILLISECONDS)
    }
  }

}
