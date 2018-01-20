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

    withSleepLoop[QiitaUserName](qiitaUserNames) { (qiitaUserName) =>
      val rawInternalUserJson = gateway.fetch(qiitaUserName)
      val crawledDateTime     = CrawledDateTime.now()
      repository.register(qiitaUserName, rawInternalUserJson, crawledDateTime)
    }
  }
}
