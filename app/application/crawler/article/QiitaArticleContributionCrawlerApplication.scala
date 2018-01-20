package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.article._
import domain.qiita.article.contribution._
import domain.qiita.article.json.RawPropsArticleJson
import scalikejdbc.DB

@Singleton
final class QiitaArticleContributionCrawlerApplication @Inject()(
    hatenaGateway:          HatenaGateway,
    facebookGateway:        FacebookGateway,
    pocketGateway:          PocketGateway,
    repository:             QiitaArticleContributionRepository,
    historyRepository:      QiitaArticleContributionHistoryRepository,
    qiitaArticleRepository: QiitaArticleRepository,
    idRepository:           QiitaArticleIdRepository,
    rawJsonRepository:      QiitaRawPropsArticleJsonRepository
) extends Crawler {

  def crawl(): Unit = {
    val items = idRepository.retrieveNotRegisteredContribution()
    withLoop[QiitaItemId](items) { (qiitaItemId, progress) =>
      quietlyCrawl(qiitaItemId, progress)
    }
  }

  private def quietlyCrawl(qiitaItemId: QiitaItemId, progress: String): Unit = {
    withQuietly[String](qiitaItemId, progress, errors) { (_) =>
      val event = crawlContribution(qiitaItemId)
      registerWithTransaction(event)
    }
  }

  private def registerWithTransaction(event: QiitaArticleContributionCrawledEvent): Unit = {
    DB.localTx { implicit session =>
      repository.register(event)
      historyRepository.register(event)
    }
  }

  private def crawlContribution(qiitaItemId: QiitaItemId): QiitaArticleContributionCrawledEvent = {
    val (hatenaCount, facebookCount, pocketCount) = crawlSocial(qiitaItemId)

    val rawArticleJson = getRawArticleJson(qiitaItemId)

    val contribution = QiitaArticleContribution(
      rawArticleJson.toLikesCount,
      rawArticleJson.toCommentsCount,
      hatenaCount,
      facebookCount,
      pocketCount
    )

    contribution.toCrawledEvent(qiitaItemId)
  }

  private def crawlSocial(qiitaItemId: QiitaItemId): (HatenaCount, FacebookCount, PocketCount) = {
    val qiitaArticle    = qiitaArticleRepository.retrieve(qiitaItemId)
    val qiitaArticleUrl = qiitaArticle.url

    val hatenaCount   = hatenaGateway.fetch(qiitaArticleUrl)
    val facebookCount = facebookGateway.fetch(qiitaArticleUrl)
    val pocketCount   = pocketGateway.fetch(qiitaArticleUrl)

    (hatenaCount, facebookCount, pocketCount)
  }

  private def getRawArticleJson(qiitaItemId: QiitaItemId): RawPropsArticleJson = {
    val optionValue = rawJsonRepository.retrieve(qiitaItemId)
    optionValue match {
      case Some(v) => v
      case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaItemId.value}")
    }
  }
}
