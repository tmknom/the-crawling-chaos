package application.crawler.user

import javax.inject.{Inject, Singleton}

import domain.qiita.initial.{QiitaUserInitialGateway, QiitaUserInitialRepository}

@Singleton
final class QiitaUserInitialCrawlerApplication @Inject()(
    gateway:    QiitaUserInitialGateway,
    repository: QiitaUserInitialRepository
) {
  def crawl(): Unit = {
    val qiitaUserInitials = gateway.fetch()
    qiitaUserInitials.foreach(repository.register)
  }
}
