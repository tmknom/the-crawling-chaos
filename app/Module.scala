import com.google.inject.AbstractModule
import domain.qiita.article.contribution.{FacebookGateway, HatenaGateway, PocketGateway, QiitaArticleContributionRepository}
import domain.qiita.article.json.QiitaRawArticleJsonRepository
import domain.qiita.article.{QiitaArticleGateway, QiitaArticleIdGateway, QiitaArticleIdRepository, QiitaArticleRepository}
import domain.qiita.user._
import domain.qiita.user.contribution._
import domain.qiita.user.ranking.{QiitaUserRankingGateway, QiitaUserRankingRepository}
import domain.qiita.user.summary.QiitaUserSummaryRepository
import infrastructure.qiita.article.contribution.ScalikejdbcQiitaArticleContributionRepository
import infrastructure.qiita.article.json.{HttpFacebookGateway, HttpHatenaGateway, HttpPocketGateway, ScalikejdbcQiitaRawArticleJsonRepository}
import infrastructure.qiita.article.{HttpQiitaArticleGateway, HttpQiitaArticleIdGateway, ScalikejdbcQiitaArticleIdRepository, ScalikejdbcQiitaArticleRepository}
import infrastructure.qiita.user._
import infrastructure.qiita.user.contribution._
import infrastructure.qiita.user.ranking.{HttpQiitaUserRankingGateway, ScalikejdbcQiitaUserRankingRepository}
import infrastructure.qiita.user.summary.ScalikejdbcQiitaUserSummaryRepository
import library.scalaj.{RealScalajHttpAdaptor, ScalajHttpAdaptor}

/**
  * This class is a Guice module that tells Guice how to bind several
  * different types. This Guice module is created when the Play
  * application starts.
  *
  * Play will automatically use any class called `Module` that is in
  * the root package. You can create modules in other locations by
  * adding `play.modules.enabled` settings to the `application.conf`
  * configuration file.
  */
class Module extends AbstractModule {

  override def configure(): Unit = {
    configureInfrastructure()
  }

  private def configureInfrastructureUser(): Unit = {
    bind(classOf[QiitaUserNameGateway]).to(classOf[HttpQiitaUserNameGateway])
    bind(classOf[QiitaUserInternalApiGateway]).to(classOf[HttpQiitaUserInternalApiGateway])

    bind(classOf[QiitaUserRepository]).to(classOf[ScalikejdbcQiitaUserRepository])
    bind(classOf[QiitaUserNameRepository]).to(classOf[ScalikejdbcQiitaUserNameRepository])
    bind(classOf[QiitaRawInternalUserJsonRepository]).to(classOf[ScalikejdbcQiitaRawInternalUserJsonRepository])
    bind(classOf[QiitaUserProfileRepository]).to(classOf[ScalikejdbcQiitaUserProfileRepository])
    bind(classOf[QiitaUserContributionRepository]).to(classOf[ScalikejdbcQiitaUserContributionRepository])
    bind(classOf[QiitaUserContributionHistoryRepository]).to(classOf[ScalikejdbcQiitaUserContributionHistoryRepository])
  }

  private def configureInfrastructure(): Unit = {
    configureInfrastructureUser()
    bind(classOf[ScalajHttpAdaptor]).to(classOf[RealScalajHttpAdaptor])

    bind(classOf[QiitaUserRankingRepository]).to(classOf[ScalikejdbcQiitaUserRankingRepository])
    bind(classOf[QiitaUserRankingGateway]).to(classOf[HttpQiitaUserRankingGateway])

    bind(classOf[QiitaUserSummaryRepository]).to(classOf[ScalikejdbcQiitaUserSummaryRepository])

    bind(classOf[QiitaArticleIdGateway]).to(classOf[HttpQiitaArticleIdGateway])
    bind(classOf[QiitaArticleGateway]).to(classOf[HttpQiitaArticleGateway])
    bind(classOf[QiitaArticleIdRepository]).to(classOf[ScalikejdbcQiitaArticleIdRepository])
    bind(classOf[QiitaArticleRepository]).to(classOf[ScalikejdbcQiitaArticleRepository])
    bind(classOf[QiitaRawArticleJsonRepository]).to(classOf[ScalikejdbcQiitaRawArticleJsonRepository])

    bind(classOf[HatenaGateway]).to(classOf[HttpHatenaGateway])
    bind(classOf[FacebookGateway]).to(classOf[HttpFacebookGateway])
    bind(classOf[PocketGateway]).to(classOf[HttpPocketGateway])
    bind(classOf[QiitaArticleContributionRepository]).to(classOf[ScalikejdbcQiitaArticleContributionRepository])
    bind(classOf[QiitaUserApiGateway]).to(classOf[HttpQiitaUserApiGateway])

    () // 明示的に Unit を返す
  }
}
