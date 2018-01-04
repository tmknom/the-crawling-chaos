package presentation.cli.operation.crawler.contribution

import library.task.Task
import play.api.{Application, Logger}
import scenario.crawler.QiitaUserContributionCrawlerScenario

/**
  * run-main presentation.cli.operation.crawler.contribution.ContributedQiitaUserContributionCrawlerCli
  */
object ContributedQiitaUserContributionCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserContributionCrawlerScenario].crawlContributedUser()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
