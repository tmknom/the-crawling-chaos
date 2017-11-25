package application.crawler

import javax.inject.{Inject, Singleton}

import domain.qiita.user.{QiitaUserGateway, QiitaUserRepository}
import domain.qiita.QiitaUserInitialRepository

@Singleton
final class QiitaUserCrawlerApplication @Inject()(
    gateway:                    QiitaUserGateway,
    repository:                 QiitaUserRepository,
    qiitaUserInitialRepository: QiitaUserInitialRepository
) {
  def crawl(): Unit = {
    val qiitaUsers = gateway.fetch()
    qiitaUsers.foreach(repository.register)
  }
}
