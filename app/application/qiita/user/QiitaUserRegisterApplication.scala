package application.qiita.user

import javax.inject.{Inject, Singleton}

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import domain.crawler.Crawler
import domain.qiita.article.contribution.HatenaCount
import domain.qiita.user.contribution.{HatenaApiGateway, QiitaUserContributionHistoryRepository, QiitaUserContributionRepository}
import domain.qiita.user.{QiitaRawInternalUserJsonRepository, QiitaUserName, QiitaUserProfileRepository, RawInternalUserJson}
import play.api.Logger
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

  def registerOne(qiitaUserName: QiitaUserName): Unit = {
    val optionValue = qiitaRawInternalUserJsonRepository.retrieve(qiitaUserName)
    val rawInternalUserJson = optionValue match {
      case Some(v) => v
      case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaUserName.value}")
    }
    val hatenaCount = hatenaApiGateway.fetch(qiitaUserName)

    registerProfile(rawInternalUserJson)
    registerContribution(rawInternalUserJson, hatenaCount)
  }

  private def registerProfile(rawInternalUserJson: RawInternalUserJson): Unit = {
    try {
      qiitaUserProfileRepository.register(rawInternalUserJson.toQiitaUserProfile)
    } catch {
      // すでに登録済みの情報を登録しようとしたら、MySQLIntegrityConstraintViolationExceptionが飛ぶ。
      // 手動でクロールを実行する場合、すでに登録済みのケースがあるので握りつぶす
      case e: MySQLIntegrityConstraintViolationException =>
        Logger.info(s"${this.getClass.getSimpleName} register error because ${e.getMessage}")
    }
  }

  private def registerContribution(rawInternalUserJson: RawInternalUserJson, hatenaCount: HatenaCount): Unit = {
    DB.localTx { implicit session =>
      val crawledEvent = rawInternalUserJson.toCrawledEvent
      qiitaUserContributionRepository.register(crawledEvent, hatenaCount)
      qiitaUserContributionHistoryRepository.register(crawledEvent, hatenaCount)
    }
  }
}
