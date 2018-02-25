package application.qiita.user

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.article.contribution.HatenaCount
import domain.qiita.user.contribution.{HatenaApiGateway, QiitaUserContributionHistoryRepository, QiitaUserContributionRepository}
import domain.qiita.user.{QiitaRawInternalUserJsonRepository, QiitaUserName, QiitaUserProfileRepository, RawInternalUserJson}
import scalikejdbc.DB

@Singleton
final class QiitaUserRegisterApplication @Inject()(
    hatenaApiGateway:                       HatenaApiGateway,
    qiitaUserProfileRepository:             QiitaUserProfileRepository,
    qiitaUserContributionRepository:        QiitaUserContributionRepository,
    qiitaUserContributionHistoryRepository: QiitaUserContributionHistoryRepository,
    qiitaRawInternalUserJsonRepository:     QiitaRawInternalUserJsonRepository
) extends Crawler {

  def registerRecently(): Unit = {
    val items = qiitaRawInternalUserJsonRepository.retrieveRecently()
    withoutSleepLoop[QiitaUserName](items)(registerOne)
  }

  private def registerOne(qiitaUserName: QiitaUserName): Unit = {
    val optionValue = qiitaRawInternalUserJsonRepository.retrieve(qiitaUserName)
    val rawInternalUserJson = optionValue match {
      case Some(v) => v
      case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaUserName.value}")
    }
    val hatenaCount = hatenaApiGateway.fetch(qiitaUserName)

    registerWithTransaction(rawInternalUserJson, hatenaCount)
  }

  private def registerWithTransaction(rawInternalUserJson: RawInternalUserJson, hatenaCount: HatenaCount): Unit = {
    DB.localTx { implicit session =>
      qiitaUserProfileRepository.register(rawInternalUserJson.toQiitaUserProfile)

      val crawledEvent = rawInternalUserJson.toCrawledEvent
      qiitaUserContributionRepository.register(crawledEvent, hatenaCount)
      qiitaUserContributionHistoryRepository.register(crawledEvent, hatenaCount)
    }
  }
}
