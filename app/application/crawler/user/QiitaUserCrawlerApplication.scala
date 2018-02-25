package application.crawler.user

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.user._
import domain.qiita.user.contribution.{HatenaApiGateway, QiitaUserContributionHistoryRepository, QiitaUserContributionRepository}
import scalikejdbc.DB

@Singleton
final class QiitaUserCrawlerApplication @Inject()(
    gateway:                                QiitaUserInternalApiGateway,
    hatenaApiGateway:                       HatenaApiGateway,
    qiitaRawInternalUserJsonRepository:     QiitaRawInternalUserJsonRepository,
    qiitaUserContributionRepository:        QiitaUserContributionRepository,
    qiitaUserContributionHistoryRepository: QiitaUserContributionHistoryRepository,
    qiitaUserNameRepository:                QiitaUserNameRepository
) extends Crawler {

  def crawlTopUser(): Unit = {
    val items = qiitaUserNameRepository.retrieveTopUser()
    crawlUsers(items)
  }

  def crawlContributedUser(): Unit = {
    val items = qiitaUserNameRepository.retrieveContributedUser()
    crawlUsers(items)
  }

  private def crawlUsers(items: List[QiitaUserName]): Unit = {
    withSleepLoop[QiitaUserName](items)(crawlOne)
  }

  private def crawlOne(qiitaUserName: QiitaUserName): Unit = {
    val rawInternalUserJson = gateway.fetch(qiitaUserName)
    val hatenaCount         = hatenaApiGateway.fetch(qiitaUserName)
    val crawledEvent        = rawInternalUserJson.toCrawledEvent

    DB.localTx { implicit session =>
      qiitaRawInternalUserJsonRepository.register(qiitaUserName, rawInternalUserJson, crawledEvent.crawledDateTime)
      qiitaUserContributionRepository.register(crawledEvent, hatenaCount)
      qiitaUserContributionHistoryRepository.register(crawledEvent, hatenaCount)
    }
  }
}
