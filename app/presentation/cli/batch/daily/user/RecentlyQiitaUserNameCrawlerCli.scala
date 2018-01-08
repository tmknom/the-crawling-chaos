package presentation.cli.batch.daily.user

import application.crawler.user.RecentlyQiitaUserNameCrawlerApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.batch.daily.user.RecentlyQiitaUserNameCrawlerCli
  */
object RecentlyQiitaUserNameCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[RecentlyQiitaUserNameCrawlerApplication].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
