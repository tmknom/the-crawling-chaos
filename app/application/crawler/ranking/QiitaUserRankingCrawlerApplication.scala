package application.crawler.ranking

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.ranking.{QiitaUserRankingGateway, QiitaUserRankingRepository}
import play.api.Logger

@Singleton
final class QiitaUserRankingCrawlerApplication @Inject()(
    gateway:    QiitaUserRankingGateway,
    repository: QiitaUserRankingRepository
) {

  private val SleepTimeMilliseconds = 250.toLong

  def crawl(): Unit = {
    (1 to 1184).zipWithIndex.foreach {
      case (page, index) =>
        val qiitaUserRankings = gateway.fetch(page)
        qiitaUserRankings.foreach(repository.register)
        Logger.info(s"crawled page ${index + 1}")
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }

}
