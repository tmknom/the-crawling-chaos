package presentation.cli.batch.daily.user

import library.task.Task
import play.api.{Application, Logger}
import scenario.user.QiitaUserCrawlerScenario

/**
  * run-main presentation.cli.batch.daily.user.QiitaUserCrawlerCli
  */
object QiitaUserCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserCrawlerScenario].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
