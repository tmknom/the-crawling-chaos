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
    val qiitaItemIds = idRepository.retrieveNotRegistered()
    withLoop[QiitaItemId](qiitaItemIds) { (qiitaItemId, progress) =>
      quietlyRegister(qiitaItemId, progress)
    }
  }

  private def quietlyRegister(qiitaItemId: QiitaItemId, progress: String): Unit = {
    withoutSleep[String](qiitaItemId, progress, errors) { (_) =>
      val optionValue = rawJsonRepository.retrieve(qiitaItemId)
      val rawArticleJson = optionValue match {
        case Some(v) => v
        case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaItemId.value}")
      }
      repository.register(rawArticleJson.toQiitaArticle)
    }
  }
}
