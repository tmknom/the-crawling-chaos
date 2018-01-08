package application.crawler.user

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.initial.QiitaUserInitialRepository
import domain.qiita.user.{QiitaUserNameGateway, QiitaUserRepository, RegisteredDateTime}
import play.api.Logger

@Singleton
final class QiitaUserCrawlerApplication @Inject()(
    gateway:                    QiitaUserNameGateway,
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
        val qiitaUserNames     = gateway.fetch(url)
        qiitaUserNames.foreach(repository.register(_, registeredDateTime))

        Logger.info(s"crawled ${qiitaUserInitial.initial.value} ($currentPage / ${qiitaUserInitial.page.value})")
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
      }
    }
  }

}
