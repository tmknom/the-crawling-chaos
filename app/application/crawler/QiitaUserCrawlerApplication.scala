package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.initial.QiitaUserInitialRepository
import domain.qiita.user.{QiitaUserGateway, QiitaUserRepository, RegisteredDateTime}
import play.api.Logger

@Singleton
final class QiitaUserCrawlerApplication @Inject()(
    gateway:                    QiitaUserGateway,
    repository:                 QiitaUserRepository,
    qiitaUserInitialRepository: QiitaUserInitialRepository
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    val qiitaUserInitials = qiitaUserInitialRepository.retrieveAll()
    qiitaUserInitials.foreach { qiitaUserInitial =>
      qiitaUserInitial.pageRange.foreach { currentPage =>
        val registeredDateTime = RegisteredDateTime.now()
        val url                = qiitaUserInitial.usersUrl(currentPage)
        val qiitaUserNams      = gateway.fetch(url)
        qiitaUserNams.foreach(repository.register(_, registeredDateTime))

        Logger.info(s"crawled ${qiitaUserInitial.initial.value} ($currentPage / ${qiitaUserInitial.page.value})")
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
      }
    }
  }

}
