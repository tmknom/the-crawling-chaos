package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.userranking.{QiitaUserRankingGateway, QiitaUserRankingRepository}

@Singleton
final class QiitaUserRankingCrawlerApplication @Inject()(
    gateway:    QiitaUserRankingGateway,
    repository: QiitaUserRankingRepository
) {

  def crawl(): Unit = {
    (1 to 50).foreach { page =>
      val qiitaUserRankings = gateway.fetch(page)
      qiitaUserRankings.foreach(repository.register)
      TimeUnit.SECONDS.sleep(1)
    }
  }

}
