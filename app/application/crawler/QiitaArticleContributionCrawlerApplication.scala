package application.crawler

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.article.json.QiitaRawArticleJsonRepository
import domain.qiita.article.{QiitaArticleGateway, QiitaArticleRepository, QiitaItemId}
import play.api.Logger

@Singleton
final class QiitaArticleContributionCrawlerApplication @Inject()(
    gateway:           QiitaArticleGateway,
    repository:        QiitaArticleRepository,
    rawJsonRepository: QiitaRawArticleJsonRepository
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    val qiitaItemIds = List.empty[QiitaItemId]
    qiitaItemIds.zipWithIndex.foreach {
      case (qiitaItemId, index) =>
        quietlyCrawl(qiitaItemId, index, qiitaItemIds.size)
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }

  private def quietlyCrawl(qiitaItemId: QiitaItemId, index: Int, qiitaItemIdsCount: Int): Unit = {
    try {
      log(qiitaItemId, index, qiitaItemIdsCount)
    } catch {
      case e: Exception =>
        Logger.warn(s"${this.getClass.getSimpleName} crawl error ${qiitaItemId.value}.", e)
    }
  }

  private def log(qiitaItemId: QiitaItemId, index: Int, qiitaItemIdsCount: Int) = {
    val progress = ((index + 1) / qiitaItemIdsCount) * 100.0
    Logger.info(s"${this.getClass.getSimpleName} crawled ${qiitaItemId.value} : ${index + 1} / $qiitaItemIdsCount ($progress%)")
  }
}
