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
    withLoop[QiitaUserName](qiitaUserNames) { (qiitaUserName, progress) =>
      quietlyCrawlOneUser(qiitaUserName, progress)
    }
  }

  private def quietlyCrawlOneUser(qiitaUserName: QiitaUserName, progress: String): Unit = {
    withSleep[String](qiitaUserName, progress, errors) { (_) =>
      val rawInternalUserJson = gateway.fetch(qiitaUserName)
      val crawledDateTime     = CrawledDateTime.now()
      repository.register(qiitaUserName, rawInternalUserJson, crawledDateTime)
    }
  }
}
