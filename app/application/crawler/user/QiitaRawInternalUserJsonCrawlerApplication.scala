package application.crawler.user

import javax.inject.{Inject, Singleton}

import domain.crawler.{Progress, QuietlyCrawler}
import domain.qiita.user._
import play.api.Logger

import scala.collection.mutable

@Singleton
final class QiitaRawInternalUserJsonCrawlerApplication @Inject()(
    gateway:                 QiitaUserInternalApiGateway,
    repository:              QiitaRawInternalUserJsonRepository,
    qiitaUserNameRepository: QiitaUserNameRepository
) extends QuietlyCrawler {
  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errorQiitaUserNames = mutable.ListBuffer.empty[String]

  def crawl(): Unit = {
    val qiitaUserNames = qiitaUserNameRepository.retrieveRecently()
    val itemsCount     = qiitaUserNames.size
    qiitaUserNames.zipWithIndex.foreach {
      case (qiitaUserName, index) =>
        val progress = Progress.calculate(index, itemsCount)
        quietlyCrawlOneUser(qiitaUserName, progress)
    }
    Logger.warn(s"${this.getClass.getSimpleName} crawl error ${errorQiitaUserNames.size.toString} users : ${errorQiitaUserNames.toString()}")
  }

  private def quietlyCrawlOneUser(qiitaUserName: QiitaUserName, progress: String): Unit = {
    withQuietly[String](qiitaUserName, progress, errorQiitaUserNames) { (_) =>
      val rawInternalUserJson = gateway.fetch(qiitaUserName)
      val crawledDateTime     = CrawledDateTime.now()
      repository.register(qiitaUserName, rawInternalUserJson, crawledDateTime)
    }
  }
}
