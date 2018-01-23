package application.content

import javax.inject.{Inject, Singleton}

import domain.json.FileWriter
import domain.qiita.user.{QiitaUser, QiitaUserRepository}
import presentation.controller.internal.MapJsonProtocol._
import spray.json._

@Singleton
final class QiitaUserContributionRankingApplication @Inject()(
    repository: QiitaUserRepository
) {
  private val LIMIT = 100

  def create(): Unit = {
    val max = repository.countContribution()
    pageRange(max).foreach { page =>
      val offset     = LIMIT * (page - 1)
      val qiitaUsers = repository.retrieveContribution(LIMIT, offset)
      val json = qiitaUsers.zipWithIndex.map {
        case (qiitaUser, index) =>
          QiitaUserJson.build(qiitaUser, index + offset + 1)
      }.toJson
      val fileName = s"/tmp/user.article.${page.toString}.json"
      FileWriter.write(fileName, json)
    }
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
      "contribution" -> qiitaUser.qiitaUserContribution.contribution.value,
      "articles_count" -> qiitaUser.qiitaUserContribution.articlesCount.value
    )
  }
}
