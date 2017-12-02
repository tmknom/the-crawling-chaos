import com.google.inject.AbstractModule
import domain.qiita.initial.{QiitaUserInitialGateway, QiitaUserInitialRepository}
import domain.qiita.user.contribution.{QiitaUserContributionGateway, QiitaUserContributionRepository}
import domain.qiita.user.ranking.{QiitaUserRankingGateway, QiitaUserRankingRepository}
import domain.qiita.user.{QiitaUserGateway, QiitaUserRepository}
import infrastructure.qiita.initial.{HttpQiitaUserInitialGateway, ScalikejdbcQiitaUserInitialRepository}
import infrastructure.qiita.user.contribution.{HttpQiitaUserContributionGateway, ScalikejdbcQiitaUserContributionRepository}
import infrastructure.qiita.user.ranking.{HttpQiitaUserRankingGateway, ScalikejdbcQiitaUserRankingRepository}
import infrastructure.qiita.user.{HttpQiitaUserGateway, ScalikejdbcQiitaUserRepository}
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

  private def configureInfrastructure(): Unit = {
    bind(classOf[ScalajHttpAdaptor]).to(classOf[RealScalajHttpAdaptor])
    bind(classOf[QiitaUserInitialRepository]).to(classOf[ScalikejdbcQiitaUserInitialRepository])
    bind(classOf[QiitaUserInitialGateway]).to(classOf[HttpQiitaUserInitialGateway])
    bind(classOf[QiitaUserRepository]).to(classOf[ScalikejdbcQiitaUserRepository])
    bind(classOf[QiitaUserGateway]).to(classOf[HttpQiitaUserGateway])
    bind(classOf[QiitaUserRankingRepository]).to(classOf[ScalikejdbcQiitaUserRankingRepository])
    bind(classOf[QiitaUserRankingGateway]).to(classOf[HttpQiitaUserRankingGateway])
    bind(classOf[QiitaUserContributionRepository]).to(classOf[ScalikejdbcQiitaUserContributionRepository])
    bind(classOf[QiitaUserContributionGateway]).to(classOf[HttpQiitaUserContributionGateway])

    () // 明示的に Unit を返す
  }
}
