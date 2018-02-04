package application.crawler.article

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.article._

@Singleton
final class QiitaArticleMarkdownRegisterApplication @Inject()(
    repository:        QiitaArticleMarkdownRepository,
    rawJsonRepository: QiitaRawArticleJsonRepository,
    idRepository:      QiitaArticleIdRepository
) extends Crawler {

  def register(): Unit = {
    val items = idRepository.retrieveNotRegisteredMarkdown()
    withoutSleepLoop[QiitaItemId](items)(registerOne)
  }

  def registerOne(qiitaItemId: QiitaItemId): Unit = {
    val optionValue = rawJsonRepository.retrieve(qiitaItemId)
    val rawArticleJson = optionValue match {
      case Some(v) => v
      case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaItemId.value}")
    }
    repository.register(rawArticleJson.toMarkdownCrawledEvent(qiitaItemId))
  }
}
