package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.{Progress, Sleeper}
import domain.qiita.article._
import domain.qiita.user.CrawledDateTime
import play.api.Logger

import scala.collection.mutable

@Singleton
final class QiitaRawArticleJsonCrawlerApplication @Inject()(
    gateway:                  QiitaArticleApiGateway,
    repository:               QiitaRawArticleJsonRepository,
    qiitaArticleIdRepository: QiitaArticleIdRepository
) {
  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errors = mutable.ListBuffer.empty[String]

  def crawl(): Unit = {
    val qiitaItemIds = qiitaArticleIdRepository.retrieveNotRegisteredRawJson()
    val itemsCount   = qiitaItemIds.size
    qiitaItemIds.zipWithIndex.foreach {
      case (qiitaItemId, index) =>
        val progress = Progress.calculate(index, itemsCount)
        quietlyCrawl(qiitaItemId, progress)
    }
    Logger.warn(s"${this.getClass.getSimpleName} crawl error ${errors.size.toString} items : ${errors.toString()}")
  }

  private def quietlyCrawl(qiitaItemId: QiitaItemId, progress: String): Unit = {
    try {
      val rawJson         = gateway.fetch(qiitaItemId)
      val crawledDateTime = CrawledDateTime.now()
      repository.register(qiitaItemId, rawJson, crawledDateTime)
      Logger.info(s"${this.getClass.getSimpleName} crawled ${qiitaItemId.value} $progress")
    } catch {
      case e: Exception =>
        errors += qiitaItemId.value
        Logger.warn(s"${this.getClass.getSimpleName} crawl error ${qiitaItemId.value}.", e)
    } finally {
      Sleeper.sleep()
    }
  }
}
