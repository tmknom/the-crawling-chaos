package presentation.cli.operation.crawler.contribution

import library.task.Task
import play.api.{Application, Logger}
import scenario.crawler.QiitaUserContributionCrawlerScenario

/**
  * run-main presentation.cli.operation.crawler.contribution.Top1000QiitaUserContributionCrawlerCli
  */
object Top1000QiitaUserContributionCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserContributionCrawlerScenario].crawlTop1000User()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
