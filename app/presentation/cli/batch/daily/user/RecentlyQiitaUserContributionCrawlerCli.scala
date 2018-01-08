package presentation.cli.batch.daily.user

import library.task.Task
import play.api.{Application, Logger}
import scenario.crawler.QiitaUserContributionCrawlerScenario

/**
  * run-main presentation.cli.batch.daily.user.RecentlyQiitaUserContributionCrawlerCli
  */
object RecentlyQiitaUserContributionCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserContributionCrawlerScenario].crawlRecentlyUser()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
