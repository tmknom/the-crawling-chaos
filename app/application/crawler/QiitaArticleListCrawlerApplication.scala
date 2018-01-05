package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.article.page.QiitaArticlePage
import domain.qiita.article.{QiitaArticleGateway, QiitaArticleListGateway, QiitaArticleRepository}
import play.api.Logger

@Singleton
final class QiitaArticleListCrawlerApplication @Inject()(
    qiitaArticleListGateway: QiitaArticleListGateway,
    qiitaArticleGateway:     QiitaArticleGateway,
    repository:              QiitaArticleRepository
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    QiitaArticlePage.range.foreach { currentPage =>
      val articles = qiitaArticleListGateway.fetch(currentPage)

      articles.foreach { article =>
        val (postedDateTime, body) = qiitaArticleGateway.fetch(article.itemId)
        repository.register(article)
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
      }

      Logger.info(s"crawled $currentPage / ${QiitaArticlePage.PageMax}")
      TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }

}
