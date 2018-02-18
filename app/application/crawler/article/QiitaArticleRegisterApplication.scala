package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.article.json.RawPropsArticleJson
import domain.qiita.article.{QiitaArticleIdRepository, QiitaArticleRepository, QiitaItemId, QiitaRawPropsArticleJsonRepository}
import play.api.Logger

@Singleton
final class QiitaArticleRegisterApplication @Inject()(
    repository:        QiitaArticleRepository,
    rawJsonRepository: QiitaRawPropsArticleJsonRepository,
    idRepository:      QiitaArticleIdRepository
) extends Crawler {

  def register(): Unit = {
    val items = idRepository.retrieveNotRegistered()
    withoutSleepLoop[QiitaItemId](items)(registerOne)
    logNotRegisteredUserName()
  }

  private def registerOne(qiitaItemId: QiitaItemId): Unit = {
    val optionValue = rawJsonRepository.retrieve(qiitaItemId)
    val rawArticleJson = optionValue match {
      case Some(v) => v
      case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaItemId.value}")
    }
    repository.register(rawArticleJson.toQiitaArticle)
  }

  private def logNotRegisteredUserName(): Unit = {
    val qiitaItemIds = errors.map(QiitaItemId(_))
    val RawPropsArticleJsons: Seq[RawPropsArticleJson] = rawJsonRepository.retrieveList(qiitaItemIds)
    val postedUserNames:      Seq[String]              = RawPropsArticleJsons.map(_.toQiitaArticle.postedUserName.value).distinct
    Logger.warn(s"${this.getClass.getSimpleName} error ${postedUserNames.size.toString} users : ${postedUserNames.toString()}")
  }
}
