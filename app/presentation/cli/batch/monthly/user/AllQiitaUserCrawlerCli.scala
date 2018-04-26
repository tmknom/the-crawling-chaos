package presentation.cli.batch.monthly.user

import application.crawler.user.QiitaUserCrawlerApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.batch.monthly.user.AllQiitaUserCrawlerCli
  */
object AllQiitaUserCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserCrawlerApplication].crawlAllUser()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
