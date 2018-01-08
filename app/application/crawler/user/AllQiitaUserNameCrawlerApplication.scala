package application.crawler.user

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.initial.{Initial, Initials}
import domain.qiita.user.{QiitaUserNameGateway, QiitaUserNameRepository}
import library.scalaj.ScalajHttpException
import play.api.Logger

import scala.util.control.Breaks.{break, breakable}

@Singleton
final class AllQiitaUserNameCrawlerApplication @Inject()(
    repository: QiitaUserNameRepository,
    gateway:    QiitaUserNameGateway
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    Initials.all().value.foreach { initial =>
      crawlOneInitial(initial)

      Logger.info(s"${this.getClass.getSimpleName} crawled ${initial.toString}")
      TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }

  private def crawlOneInitial(initial: Initial): Unit = {
    breakable {
      var currentPage = 0
      while (true) {
        try {
          currentPage += 1
          crawlOnePage(initial, currentPage)
        } catch {
          case e: ScalajHttpException => {
            Logger.info(s"${this.getClass.getSimpleName} crawl end ${initial.value}")
            break
          }
        }
      }
    }
  }

  private def crawlOnePage(initial: Initial, currentPage: Int): Unit = {
    val qiitaUserNames = gateway.fetch(initial.usersUrl(currentPage))
    qiitaUserNames.foreach(repository.register)
    Logger.info(s"${this.getClass.getSimpleName} crawled ${initial.toString}#${currentPage.toString}")
  }
}
