package presentation.cli.operation.crawler

import application.crawler.QiitaUserContributionCrawlerApplication
import library.adaptor.DispatchAdaptor
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.operation.crawler.QiitaUserContributionCrawlerCli
  */
object QiitaUserContributionCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserContributionCrawlerApplication].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    } finally {
      DispatchAdaptor.close() // これがないと JVM がシャットダウンせず、プロセスが刺さりっぱなしになる
    }
  }
}
