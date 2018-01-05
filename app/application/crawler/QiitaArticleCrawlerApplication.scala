package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import play.api.Logger

@Singleton
final class QiitaArticleCrawlerApplication @Inject()(
                                                    ) {

  private val PageMax = 15000
  private val PageMin = 1

  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    pageRange.foreach { currentPage =>
      // ページを取得してパース
      val articles = List.empty[AnyVal]
      // 記事の集合をそれぞれ保存
      articles.foreach { article =>

      }
      Logger.info(s"crawled $currentPage / $PageMax")
      TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }

  private def pageRange: Range = {
    PageMin to PageMax
  }
}
