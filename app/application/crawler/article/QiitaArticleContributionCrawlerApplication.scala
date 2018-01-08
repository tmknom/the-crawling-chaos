package application.crawler.article

import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import domain.qiita.article.contribution.{QiitaArticleContributionRepository, _}
import domain.qiita.article.json.{QiitaRawArticleJsonRepository, RawArticleJson}
import domain.qiita.article.{QiitaArticleRepository, QiitaItemId}
import play.api.Logger

@Singleton
final class QiitaArticleContributionCrawlerApplication @Inject()(
    repository:             QiitaArticleContributionRepository,
    hatenaGateway:          HatenaGateway,
    facebookGateway:        FacebookGateway,
    pocketGateway:          PocketGateway,
    qiitaArticleRepository: QiitaArticleRepository,
    rawJsonRepository:      QiitaRawArticleJsonRepository
) {
  private val SleepTimeMilliseconds = 100.toLong

  def crawl(): Unit = {
    val items = rawJsonRepository.retrieveAll()
    items.zipWithIndex.foreach {
      case ((qiitaItemId, rawArticleJson), index) =>
        quietlyCrawl(qiitaItemId, rawArticleJson, index, items.size)
        TimeUnit.MILLISECONDS.sleep(SleepTimeMilliseconds)
    }
  }

  private def quietlyCrawl(qiitaItemId: QiitaItemId, rawArticleJson: RawArticleJson, index: Int, itemsCount: Int): Unit = {
    try {
      val qiitaArticle    = qiitaArticleRepository.retrieve(qiitaItemId)
      val qiitaArticleUrl = qiitaArticle.url

      val hatenaCount   = hatenaGateway.fetch(qiitaArticleUrl)
      val facebookCount = facebookGateway.fetch(qiitaArticleUrl)
      val pocketCount   = pocketGateway.fetch(qiitaArticleUrl)

      val qiitaArticleContribution = QiitaArticleContribution(
        rawArticleJson.toLikesCount,
        rawArticleJson.toCommentsCount,
        hatenaCount,
        facebookCount,
        pocketCount
      )
      repository.register(qiitaArticle.id, qiitaArticleContribution)
      log(qiitaItemId, index, itemsCount)
    } catch {
      case e: Exception =>
        Logger.warn(s"${this.getClass.getSimpleName} crawl error ${qiitaItemId.value}.", e)
    }
  }

  private def log(qiitaItemId: QiitaItemId, index: Int, itemsCount: Int) = {
    val progress = ((index + 1) / itemsCount) * 100.0
    Logger.info(s"${this.getClass.getSimpleName} crawled ${qiitaItemId.value} : ${index + 1} / $itemsCount ($progress%)")
  }
}
