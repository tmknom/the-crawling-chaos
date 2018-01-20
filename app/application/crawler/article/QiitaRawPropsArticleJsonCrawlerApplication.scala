package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.article._
import domain.qiita.user.CrawledDateTime

@Singleton
final class QiitaRawPropsArticleJsonCrawlerApplication @Inject()(
    gateway:                  QiitaArticleInternalApiGateway,
    repository:               QiitaRawPropsArticleJsonRepository,
    qiitaArticleIdRepository: QiitaArticleIdRepository
) extends Crawler {

  def crawl(): Unit = {
    val qiitaItemIds = qiitaArticleIdRepository.retrieveRecently()
    withSleepLoop[QiitaItemId](qiitaItemIds)(crawlOne)
  }

  def crawlOne(qiitaItemId: QiitaItemId): Unit = {
    val rawArticleJson  = gateway.fetch(qiitaItemId)
    val crawledDateTime = CrawledDateTime.now()
    repository.register(qiitaItemId, rawArticleJson, crawledDateTime)
  }
}
