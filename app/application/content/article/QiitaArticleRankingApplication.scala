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
  private val LIMIT            = 100
  private var lastContribution = -1
  private var lastRank         = -1

  def create(): Unit = {
    createQiita()
    createCommentsCount()
    createHatenaCount()
    createFacebookCount()
    createPocketCount()
  }

  private def createQiita(): Unit = {
    init()
    val qiitaArticleAggregates = repository.retrieveContribution()
    createJsonFileAll("qiita", qiitaArticleAggregates)
  }

  private def createCommentsCount(): Unit = {
    init()
    val qiitaArticleAggregates = repository.retrieveCommentsCount()
    createJsonFileAll("comment", qiitaArticleAggregates)
  }

  private def createHatenaCount(): Unit = {
    init()
    val qiitaArticleAggregates = repository.retrieveHatenaCount()
    createJsonFileAll("hatena", qiitaArticleAggregates)
  }

  private def createFacebookCount(): Unit = {
    init()
    val qiitaArticleAggregates = repository.retrieveFacebookCount()
    createJsonFileAll("facebook", qiitaArticleAggregates)
  }

  private def createPocketCount(): Unit = {
    init()
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
        val rank = calculateRank(offset, index, fileType, qiitaArticleAggregate)
        QiitaArticleJson.build(qiitaArticleAggregate, rank)
    }.toJson

    val fileName = s"/tmp/article.$fileType.${page.toString}.json"
    FileWriter.write(fileName, json)
  }

  private def calculateRank(offset: Int, index: Int, fileType: String, qiitaArticleAggregate: QiitaArticleAggregate): Int = {
    val count = fileType match {
      case "qiita"    => qiitaArticleAggregate.contribution.likesCount.value
      case "comment"  => qiitaArticleAggregate.contribution.commentsCount.value
      case "hatena"   => qiitaArticleAggregate.contribution.hatenaCount.value
      case "facebook" => qiitaArticleAggregate.contribution.facebookCount.value
      case "pocket"   => qiitaArticleAggregate.contribution.pocketCount.value
    }

    if (lastContribution != count) {
      lastContribution = count
      lastRank         = offset + index + 1
    }
    lastRank
  }

  private def init(): Unit = {
    lastContribution = -1
    lastRank         = -1
  }

  private def pageRange(max: Long): Range = {
    1 to Math.floor(max / LIMIT).toInt
  }
}
