package application.content.user

import javax.inject.{Inject, Singleton}

import domain.json.FileWriter
import domain.qiita.user.{QiitaUser, QiitaUserRepository}
import presentation.controller.internal.MapJsonProtocol._
import spray.json._

@Singleton
final class QiitaUserRankingApplication @Inject()(
    repository: QiitaUserRepository
) {
  private val LIMIT     = 100
  private var lastCount = -1
  private var lastRank  = -1

  def create(): Unit = {
    createTotalEvaluation()
    createContribution()
    createArticlesCount()
  }

  private def createTotalEvaluation(): Unit = {
    init()
    val max = repository.countTotalEvaluation()
    pageRange(max).foreach { page =>
      val offset     = LIMIT * (page - 1)
      val qiitaUsers = repository.retrieveTotalEvaluation(LIMIT, offset)
      createJsonFile(page, offset, "total", qiitaUsers)
    }
  }

  private def createContribution(): Unit = {
    init()
    val max = repository.countContribution()
    pageRange(max).foreach { page =>
      val offset     = LIMIT * (page - 1)
      val qiitaUsers = repository.retrieveContribution(LIMIT, offset)
      createJsonFile(page, offset, "contribution", qiitaUsers)
    }
  }

  private def createArticlesCount(): Unit = {
    init()
    val max = repository.countArticlesCount()
    pageRange(max).foreach { page =>
      val offset     = LIMIT * (page - 1)
      val qiitaUsers = repository.retrieveArticlesCount(LIMIT, offset)
      createJsonFile(page, offset, "articles_count", qiitaUsers)
    }
  }

  private def createJsonFile(page: Int, offset: Int, fileType: String, qiitaUsers: Seq[QiitaUser]): Unit = {
    val json = qiitaUsers.zipWithIndex.map {
      case (qiitaUser, index) =>
        val rank = calculateRank(offset, index, fileType, qiitaUser)
        if (fileType == "total") {
          createPersonalJsonFile(qiitaUser, rank)
        }
        QiitaUserJson.build(qiitaUser, rank)
    }.toJson

    val fileName = s"/tmp/user.$fileType.${page.toString}.json"
    FileWriter.write(fileName, json)
  }

  private def createPersonalJsonFile(qiitaUser: QiitaUser, rank: Int): Unit = {
    val fileName = s"/tmp/user/${qiitaUser.profile.name.value}.json"
    FileWriter.write(fileName, QiitaUserJson.build(qiitaUser, rank).toJson)
  }

  private def calculateRank(offset: Int, index: Int, fileType: String, qiitaUser: QiitaUser): Int = {
    val count = fileType match {
      case "total"          => qiitaUser.qiitaUserContribution.totalEvaluation.value
      case "contribution"   => qiitaUser.qiitaUserContribution.contribution.value
      case "articles_count" => qiitaUser.qiitaUserContribution.articlesCount.value
    }

    if (lastCount != count) {
      lastCount = count
      lastRank  = offset + index + 1
    }
    lastRank
  }

  private def init(): Unit = {
    lastCount = -1
    lastRank  = -1
  }

  private def pageRange(max: Long): Range = {
    1 to (Math.floor(max / LIMIT).toInt + 1)
  }
}
