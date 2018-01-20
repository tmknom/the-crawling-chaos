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
    withLoop[QiitaItemId](qiitaItemIds) { (qiitaItemId, progress) =>
      quietlyCrawl(qiitaItemId, progress)
    }
  }

  private def quietlyCrawl(qiitaItemId: QiitaItemId, progress: String): Unit = {
    withQuietly[String](qiitaItemId, progress, errors) { (_) =>
      val rawJson         = gateway.fetch(qiitaItemId)
      val crawledDateTime = CrawledDateTime.now()
      repository.register(qiitaItemId, rawJson, crawledDateTime)
    }
  }
}
