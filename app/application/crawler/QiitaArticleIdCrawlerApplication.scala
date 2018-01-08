package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.article.page.QiitaArticlePage
import domain.qiita.article.{QiitaArticleIdGateway, QiitaArticleIdRepository}
import play.api.Logger

@Singleton
final class QiitaArticleIdCrawlerApplication @Inject()(
    gateway:    QiitaArticleIdGateway,
    repository: QiitaArticleIdRepository
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    QiitaArticlePage.range.foreach { currentPage =>
      quietlyCrawl(currentPage)
      TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }

  private def quietlyCrawl(currentPage: Int): Unit = {
    try {
      val qiitaItemIds = gateway.fetch(currentPage)
      qiitaItemIds.foreach(repository.register)
      Logger.info(s"crawled $currentPage / ${QiitaArticlePage.PageMax}")
    } catch {
      case e: Exception =>
        Logger.warn(s"${this.getClass.getSimpleName} crawl error ${currentPage.toString}.", e)
    }
  }
}
