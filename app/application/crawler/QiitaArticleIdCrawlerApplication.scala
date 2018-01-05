package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.article.page.QiitaArticlePage
import domain.qiita.article.{QiitaArticleIdGateway, QiitaArticleIdRepository, QiitaItemId}
import play.api.Logger

@Singleton
final class QiitaArticleIdCrawlerApplication @Inject()(
    qiitaArticleListGateway: QiitaArticleIdGateway,
    repository:              QiitaArticleIdRepository
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
      val articles = qiitaArticleListGateway.fetch(currentPage)
      articles.foreach(article => quietlyRegister(article.itemId))
      Logger.info(s"crawled $currentPage / ${QiitaArticlePage.PageMax}")
    } catch {
      case e: Exception =>
        Logger.warn(s"${this.getClass.getSimpleName} crawl error ${currentPage.toString}.", e)
    }
  }

  private def quietlyRegister(qiitaItemId: QiitaItemId): Unit = {
    try {
      repository.register(qiitaItemId)
    } catch {
      case e: Exception =>
        Logger.warn(s"${this.getClass.getSimpleName} register error ${qiitaItemId.value}.", e)
    }
  }
}
