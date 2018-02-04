package presentation.cli.batch.daily.article

import library.task.Task
import play.api.{Application, Logger}
import scenario.article.QiitaArticleCrawlerScenario

/**
  * run-main presentation.cli.batch.daily.article.QiitaArticleCrawlerCli
  */
object QiitaArticleCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaArticleCrawlerScenario].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
