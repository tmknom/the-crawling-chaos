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
    val items = qiitaArticleIdRepository.retrieveNotRegisteredRawJson()
    withSleepLoop[QiitaItemId](items)(crawlOne)
  }

  def crawlOne(qiitaItemId: QiitaItemId): Unit = {
    val rawJson         = gateway.fetch(qiitaItemId)
    val crawledDateTime = CrawledDateTime.now()
    repository.register(qiitaItemId, rawJson, crawledDateTime)
  }
}
