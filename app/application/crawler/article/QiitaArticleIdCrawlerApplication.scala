package application.crawler.article

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import domain.qiita.article.{QiitaArticleIdGateway, QiitaArticleIdRepository, QiitaItemId}
import play.api.Logger

import scala.collection.mutable

@Singleton
final class QiitaArticleIdCrawlerApplication @Inject()(
    gateway:    QiitaArticleIdGateway,
    repository: QiitaArticleIdRepository
) {

  /**
    * 中断するエラー件数
    */
  private val SuspendCount = 10

  private val SleepTimeMilliseconds = 100.toLong

  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errorQiitaItemIds = mutable.ListBuffer.empty[String]

  def crawl(): Unit = {
    try {
      QiitaItemId.pageRange.foreach { currentPage =>
        crawlOnePage(currentPage)
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
      }
    } catch {
      // 一定回数MySQLIntegrityConstraintViolationExceptionがスローされるまでは、
      // 処理を継続し、しきい値を超えたらSuspendExceptionが飛ぶ。
      // SuspendExceptioがスローされれば、それ以上クロールは不要と判断し、正常終了させる。
      case e: SuspendException =>
        Logger.info(s"${this.getClass.getSimpleName} crawled end because ${e.getMessage}")
    }
  }

  private def crawlOnePage(currentPage: Int): Unit = {
    val qiitaItemIds = gateway.fetch(currentPage)
    qiitaItemIds.foreach(quietlyRegister)
    Logger.info(s"crawled $currentPage / ${QiitaItemId.pageMax}")
    checkErrorCount()
  }

  private def quietlyRegister(qiitaItemId: QiitaItemId): Unit = {
    try {
      repository.register(qiitaItemId)
    } catch {
      // すでに登録済みの情報を登録しようとしたら、MySQLIntegrityConstraintViolationExceptionが飛ぶ。
      // 例外が飛ぶのは、Qiitaに記事が投稿されてページがずれた場合と、すでにクロール済みの場合。
      // 例外だけでは区別できないので、一定回数、例外がスローされるまで、処理は継続することにした。
      case e: MySQLIntegrityConstraintViolationException =>
        errorQiitaItemIds += qiitaItemId.value
        Logger.info(s"${this.getClass.getSimpleName} register error because ${e.getMessage}")
    }
  }

  private def checkErrorCount(): Unit = {
    if (errorQiitaItemIds.size > SuspendCount) {
      throw new SuspendException(errorQiitaItemIds.toString())
    }
  }

  private class SuspendException(message: String) extends RuntimeException(message)

}
