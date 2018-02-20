package presentation.cli.batch.monthly.article

import library.task.Task
import play.api.{Application, Logger}
import scenario.article.TopQiitaArticleCrawlerScenario

/**
  * run-main presentation.cli.batch.monthly.article.TopQiitaArticleCrawlerCli
  */
object TopQiitaArticleCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[TopQiitaArticleCrawlerScenario].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
