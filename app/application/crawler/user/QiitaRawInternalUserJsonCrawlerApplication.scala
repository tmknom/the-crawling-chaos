package application.crawler.user

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.user._

@Singleton
final class QiitaRawInternalUserJsonCrawlerApplication @Inject()(
    gateway:                 QiitaUserInternalApiGateway,
    repository:              QiitaRawInternalUserJsonRepository,
    qiitaUserNameRepository: QiitaUserNameRepository
) extends Crawler {

  def crawl(): Unit = {
    val qiitaUserNames = qiitaUserNameRepository.retrieveRecently()

    withSleepLoop[QiitaUserName](qiitaUserNames)(crawlOne)
  }

  def crawlOne(qiitaUserName: QiitaUserName): Unit = {
    val rawInternalUserJson = gateway.fetch(qiitaUserName)
    val crawledDateTime     = CrawledDateTime.now()
    repository.register(qiitaUserName, rawInternalUserJson, crawledDateTime)
  }
}
