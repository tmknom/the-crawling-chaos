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

  def createContribution(): Unit = {
    val qiitaArticleAggregates = repository.retrieveContribution()
    createJsonFile("contribution", qiitaArticleAggregates)
  }

  private def createJsonFile(fileType: String, qiitaArticleAggregates: Seq[QiitaArticleAggregate]): Unit = {
    val json = qiitaArticleAggregates.zipWithIndex.map {
      case (qiitaArticleAggregate, index) =>
        QiitaArticleJson.build(qiitaArticleAggregate, index + 1)
    }.toJson

    val fileName = s"/tmp/article.$fileType.json"
    FileWriter.write(fileName, json)
  }
}
