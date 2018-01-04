package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.initial.QiitaUserInitialRepository
import domain.qiita.user.{QiitaUserGateway, QiitaUserRepository, RegisteredDateTime}

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
        val registeredDateTime = RegisteredDateTime.now()
        val url                = qiitaUserInitial.usersUrl(currentPage)
        val qiitaUserNams      = gateway.fetch(url)
        qiitaUserNams.foreach(repository.register(_, registeredDateTime))
        TimeUnit.SECONDS.sleep(1)
      }
    }
  }

}
