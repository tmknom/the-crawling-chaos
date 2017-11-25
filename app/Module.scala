import com.google.inject.AbstractModule
import domain.qiita._
import domain.qiita.user.{QiitaUserGateway, QiitaUserRepository}
import domain.qiita.userranking.{QiitaUserRankingGateway, QiitaUserRankingRepository}
import infrastructure.qiita._
import infrastructure.qiita.user.{HttpQiitaUserGateway, ScalikejdbcQiitaUserRepository}
import infrastructure.qiita.userranking.{HttpQiitaUserRankingGateway, ScalikejdbcQiitaUserRankingRepository}

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

  private def configureInfrastructure() = {
    bind(classOf[QiitaUserInitialRepository]).to(classOf[ScalikejdbcQiitaUserInitialRepository])
    bind(classOf[QiitaUserInitialGateway]).to(classOf[HttpQiitaUserInitialGateway])
    bind(classOf[QiitaUserRepository]).to(classOf[ScalikejdbcQiitaUserRepository])
    bind(classOf[QiitaUserGateway]).to(classOf[HttpQiitaUserGateway])
    bind(classOf[QiitaUserRankingRepository]).to(classOf[ScalikejdbcQiitaUserRankingRepository])
    bind(classOf[QiitaUserRankingGateway]).to(classOf[HttpQiitaUserRankingGateway])
  }
}
