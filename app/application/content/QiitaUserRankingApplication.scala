package application.content

import javax.inject.{Inject, Singleton}

import domain.json.FileWriter
import domain.qiita.user.{QiitaUser, QiitaUserRepository}
import presentation.controller.internal.MapJsonProtocol._
import spray.json._

@Singleton
final class QiitaUserRankingApplication @Inject()(
    repository: QiitaUserRepository
) {
  private val LIMIT = 100

  def create(): Unit = {
    createContribution()
    createArticlesCount()
  }

  private def createContribution(): Unit = {
    val max = repository.countContribution()
    pageRange(max).foreach { page =>
      val offset     = LIMIT * (page - 1)
      val qiitaUsers = repository.retrieveContribution(LIMIT, offset)
      createJsonFile(page, offset, "contribution", qiitaUsers)
    }
  }

  private def createArticlesCount(): Unit = {
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
        QiitaUserJson.build(qiitaUser, index + offset + 1)
    }.toJson

    val fileName = s"/tmp/user.$fileType.${page.toString}.json"
    FileWriter.write(fileName, json)
  }

  private def pageRange(max: Long): Range = {
    1 to (Math.floor(max / LIMIT).toInt + 1)
  }
}

object QiitaUserJson {
  def build(qiitaUser: QiitaUser, rank: Int): Map[String, _] = {
    Map(
      "index" -> rank,
      "name" -> qiitaUser.profile.name.value,
      "profile_image_url" -> qiitaUser.profile.profileImageUrl.value,
      "contribution" -> qiitaUser.qiitaUserContribution.contribution.value,
      "articles_count" -> qiitaUser.qiitaUserContribution.articlesCount.value
    )
  }
}
