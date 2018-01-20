package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.Bulk
import domain.qiita.article.{QiitaArticleIdRepository, QiitaArticleRepository, QiitaItemId, QiitaRawPropsArticleJsonRepository}
import play.api.Logger

@Singleton
final class QiitaArticleRegisterApplication @Inject()(
    repository:        QiitaArticleRepository,
    rawJsonRepository: QiitaRawPropsArticleJsonRepository,
    idRepository:      QiitaArticleIdRepository
) extends Bulk {

  def crawl(): Unit = {
    val qiitaItemIds = idRepository.retrieveNotRegistered()
    withLoop[QiitaItemId](qiitaItemIds) { (qiitaItemId, progress) =>
      quietlyRegister(qiitaItemId, progress)
    }
  }

  private def quietlyRegister(qiitaItemId: QiitaItemId, progress: String): Unit = {
    try {
      val optionValue = rawJsonRepository.retrieve(qiitaItemId)
      val rawArticleJson = optionValue match {
        case Some(v) => v
        case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaItemId.value}")
      }

      repository.register(rawArticleJson.toQiitaArticle)
      Logger.info(s"${this.getClass.getSimpleName} registered ${qiitaItemId.value} $progress")
    } catch {
      case e: Exception =>
        errors += qiitaItemId.value
        Logger.warn(s"${this.getClass.getSimpleName} register error ${qiitaItemId.value}.", e)
    }
  }
}
