package presentation.cli.operation

import domain.qiita.user.QiitaUserName
import library.task.Task
import play.api.{Application, Logger}
import scenario.user.OneQiitaUserCrawlerScenario

/**
  * run-main presentation.cli.operation.CrawlOneQiitaUserCli "xxxx"
  */
object CrawlOneQiitaUserCli extends App with Task {
  private val qiitaUserName: QiitaUserName = QiitaUserName(args(0))

  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[OneQiitaUserCrawlerScenario].crawl(qiitaUserName)
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
