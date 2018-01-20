package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.Progress
import domain.qiita.article.{QiitaArticleIdRepository, QiitaArticleRepository, QiitaItemId, QiitaRawPropsArticleJsonRepository}
import play.api.Logger

import scala.collection.mutable

@Singleton
final class QiitaArticleRegisterApplication @Inject()(
    repository:        QiitaArticleRepository,
    rawJsonRepository: QiitaRawPropsArticleJsonRepository,
    idRepository:      QiitaArticleIdRepository
) {

  @SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures"))
  private val errors = mutable.ListBuffer.empty[String]

  def crawl(): Unit = {
    val qiitaItemIds = idRepository.retrieveNotRegistered()
    val itemsCount   = qiitaItemIds.size
    qiitaItemIds.zipWithIndex.foreach {
      case (qiitaItemId, index) =>
        val progress = Progress.calculate(index, itemsCount)
        quietlyRegister(qiitaItemId, progress)
    }
    Logger.warn(s"${this.getClass.getSimpleName} register error ${errors.size.toString} items : ${errors.toString()}")
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
