package presentation.cli.onetime

import application.crawler.user.AllQiitaUserNameCrawlerApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * 全 Qiita ユーザ名をクロール
  *
  * run-main presentation.cli.onetime.AllQiitaUserNameCrawlerCli
  */
object AllQiitaUserNameCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[AllQiitaUserNameCrawlerApplication].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
