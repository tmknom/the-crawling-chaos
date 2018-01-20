package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.article._
import domain.qiita.user.CrawledDateTime

@Singleton
final class QiitaRawArticleJsonCrawlerApplication @Inject()(
    gateway:                  QiitaArticleApiGateway,
    repository:               QiitaRawArticleJsonRepository,
    qiitaArticleIdRepository: QiitaArticleIdRepository
) extends Crawler {

  def crawl(): Unit = {
    val qiitaItemIds = qiitaArticleIdRepository.retrieveNotRegisteredRawJson()

    withSleepLoop[QiitaItemId](qiitaItemIds) { (qiitaItemId) =>
      val rawJson         = gateway.fetch(qiitaItemId)
      val crawledDateTime = CrawledDateTime.now()
      repository.register(qiitaItemId, rawJson, crawledDateTime)
    }
  }
}
