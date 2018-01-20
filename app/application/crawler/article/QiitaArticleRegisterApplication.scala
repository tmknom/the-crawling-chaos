package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.article.{QiitaArticleIdRepository, QiitaArticleRepository, QiitaItemId, QiitaRawPropsArticleJsonRepository}

@Singleton
final class QiitaArticleRegisterApplication @Inject()(
    repository:        QiitaArticleRepository,
    rawJsonRepository: QiitaRawPropsArticleJsonRepository,
    idRepository:      QiitaArticleIdRepository
) extends Crawler {

  def crawl(): Unit = {
    val items = idRepository.retrieveNotRegistered()
    withoutSleepLoop[QiitaItemId](items)(registerOne)
  }

  def registerOne(qiitaItemId: QiitaItemId): Unit = {
    val optionValue = rawJsonRepository.retrieve(qiitaItemId)
    val rawArticleJson = optionValue match {
      case Some(v) => v
      case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaItemId.value}")
    }
    repository.register(rawArticleJson.toQiitaArticle)
  }
}
