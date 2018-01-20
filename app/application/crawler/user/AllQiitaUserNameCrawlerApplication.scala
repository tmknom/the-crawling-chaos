package application.crawler.user

import javax.inject.{Inject, Singleton}

import domain.crawler.Sleeper
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

  def crawl(): Unit = {
    Initials.all().value.foreach { initial =>
      crawlOneInitial(initial)

      Logger.info(s"${this.getClass.getSimpleName} crawled ${initial.toString}")
      Sleeper.sleep()
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
