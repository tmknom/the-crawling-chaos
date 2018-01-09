package application.crawler.user

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.user.json.{CrawledDateTime, QiitaRawInternalUserJsonRepository}
import domain.qiita.user.{QiitaUserInternalApiGateway, QiitaUserName, QiitaUserNameRepository}
import library.scalaj.ScalajHttpException
import play.api.Logger

import scala.collection.mutable

@Singleton
final class AllQiitaRawInternalUserJsonCrawlerApplication @Inject()(
    gateway:                 QiitaUserInternalApiGateway,
    repository:              QiitaRawInternalUserJsonRepository,
    qiitaUserNameRepository: QiitaUserNameRepository
) {
  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errorQiitaUserNames = mutable.ListBuffer.empty[String]

  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    val qiitaUserNames = qiitaUserNameRepository.retrieveRecently()
    val itemsCount     = qiitaUserNames.size
    qiitaUserNames.zipWithIndex.foreach {
      case (qiitaUserName, index) =>
        val progress = calculateProgress(index, itemsCount)
        quietlyCrawlOneUser(qiitaUserName, progress)
    }
    Logger.warn(s"${this.getClass.getSimpleName} crawl error ${errorQiitaUserNames.size.toString} users : ${errorQiitaUserNames.toString()}")
  }

  private def quietlyCrawlOneUser(qiitaUserName: QiitaUserName, progress: String): Unit = {
    try {
      val rawInternalUserJson = gateway.fetch(qiitaUserName)
      val crawledDateTime     = CrawledDateTime.now()
      repository.register(qiitaUserName, rawInternalUserJson, crawledDateTime)
      Logger.info(s"${this.getClass.getSimpleName} crawled ${qiitaUserName.value} $progress")
    } catch {
      // 処理を止めてほしくないのでログ吐いて握りつぶす
      case e: ScalajHttpException => {
        errorQiitaUserNames += qiitaUserName.value
        Logger.warn(s"${this.getClass.getSimpleName} crawl error ${qiitaUserName.value} $progress because ${e.message}")
      }
    } finally {
      TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }

  private def calculateProgress(index: Int, itemsCount: Int): String = {
    val progress = ((index + 1) / itemsCount.toDouble) * 100.0
    s"(${index + 1} / $itemsCount = $progress%)"
  }
}
