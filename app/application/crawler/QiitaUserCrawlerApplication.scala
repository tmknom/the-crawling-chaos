package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.QiitaUserInitialRepository
import domain.qiita.user.{QiitaUserGateway, QiitaUserRepository}

@Singleton
final class QiitaUserCrawlerApplication @Inject()(
    gateway:                    QiitaUserGateway,
    repository:                 QiitaUserRepository,
    qiitaUserInitialRepository: QiitaUserInitialRepository
) {

  def crawl(): Unit = {
    val qiitaUserInitials = qiitaUserInitialRepository.retrieveAll()
    qiitaUserInitials.foreach { qiitaUserInitial =>
      qiitaUserInitial.pageRange.foreach { currentPage =>
        val url        = qiitaUserInitial.usersUrl(currentPage)
        val qiitaUsers = gateway.fetch(url)
        qiitaUsers.foreach(repository.register)
        TimeUnit.SECONDS.sleep(1)
      }
    }
  }

}
