package application.crawler.article

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import domain.qiita.article.{QiitaArticleIdGateway, QiitaArticleIdRepository, QiitaItemId}
import play.api.Logger

@Singleton
final class QiitaArticleIdCrawlerApplication @Inject()(
    gateway:    QiitaArticleIdGateway,
    repository: QiitaArticleIdRepository
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    try {
      QiitaItemId.pageRange.foreach { currentPage =>
        crawlOnePage(currentPage)
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
      }
    } catch {
      // すでに登録済みの情報を登録しようとしたら、MySQLIntegrityConstraintViolationExceptionが飛ぶ
      // あまり行儀がよくないが、重複エラーが処理終了のタイイングと合致して都合がいいので、
      // 例外を飛ばしてそいつをキャッチして終了することにした
      case e: MySQLIntegrityConstraintViolationException =>
        Logger.info(s"${this.getClass.getSimpleName} crawled end because ${e.getMessage}")
    }
  }

  private def crawlOnePage(currentPage: Int): Unit = {
    val qiitaItemIds = gateway.fetch(currentPage)
    qiitaItemIds.foreach(repository.register)
    Logger.info(s"crawled $currentPage / ${QiitaItemId.pageMax}")
  }
}