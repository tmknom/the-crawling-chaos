package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.page.RecentlyPage
import domain.qiita.user.recently.RecentlyQiitaUserGateway
import domain.qiita.user.{QiitaUserGateway, QiitaUserRepository, RegisteredDateTime}
import play.api.Logger

@Singleton
final class RecentlyQiitaUserCrawlerApplication @Inject()(
    gateway:                  QiitaUserGateway,
    repository:               QiitaUserRepository,
    recentlyQiitaUserGateway: RecentlyQiitaUserGateway
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    RecentlyPage.range.foreach { currentPage =>
      val qiitaUserNames     = recentlyQiitaUserGateway.fetch(currentPage)
      val registeredDateTime = RegisteredDateTime.now()
      qiitaUserNames.foreach(repository.register(_, registeredDateTime))

      Logger.info(s"${this.getClass.getSimpleName} crawled ($currentPage / ${RecentlyPage.PageMax})")
      TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }
}
