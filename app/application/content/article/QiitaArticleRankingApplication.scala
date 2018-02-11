package application.content.article

import javax.inject.{Inject, Singleton}

import domain.json.FileWriter
import domain.qiita.article.{QiitaArticleAggregate, QiitaArticleAggregateRepository}
import presentation.controller.internal.MapJsonProtocol._
import spray.json._

@Singleton
final class QiitaArticleRankingApplication @Inject()(
    repository: QiitaArticleAggregateRepository
) {
  private val LIMIT = 100

  def create(): Unit = {
    createContribution()
    createCommentsCount()
    createHatenaCount()
    createFacebookCount()
    createPocketCount()
  }

  private def createContribution(): Unit = {
    val qiitaArticleAggregates = repository.retrieveContribution()
    createJsonFileAll("contribution", qiitaArticleAggregates)
  }

  private def createCommentsCount(): Unit = {
    val qiitaArticleAggregates = repository.retrieveCommentsCount()
    createJsonFileAll("comment", qiitaArticleAggregates)
  }

  private def createHatenaCount(): Unit = {
    val qiitaArticleAggregates = repository.retrieveHatenaCount()
    createJsonFileAll("hatena", qiitaArticleAggregates)
  }

  private def createFacebookCount(): Unit = {
    val qiitaArticleAggregates = repository.retrieveFacebookCount()
    createJsonFileAll("facebook", qiitaArticleAggregates)
  }

  private def createPocketCount(): Unit = {
    val qiitaArticleAggregates = repository.retrievePocketCount()
    createJsonFileAll("pocket", qiitaArticleAggregates)
  }

  private def createJsonFileAll(fileType: String, qiitaArticleAggregates: Seq[QiitaArticleAggregate]): Unit = {
    val max = qiitaArticleAggregates.size
    pageRange(max).foreach { page =>
      val offset = LIMIT * (page - 1)
      createJsonFile(page, offset, fileType, qiitaArticleAggregates.slice(offset, offset + LIMIT))
    }
  }

  private def createJsonFile(page: Int, offset: Int, fileType: String, qiitaArticleAggregates: Seq[QiitaArticleAggregate]): Unit = {
    val json = qiitaArticleAggregates.zipWithIndex.map {
      case (qiitaArticleAggregate, index) =>
        QiitaArticleJson.build(qiitaArticleAggregate, offset + index + 1)
    }.toJson

    val fileName = s"/tmp/article.$fileType.${page.toString}.json"
    FileWriter.write(fileName, json)
  }

  private def pageRange(max: Long): Range = {
    1 to Math.floor(max / LIMIT).toInt
  }
}
