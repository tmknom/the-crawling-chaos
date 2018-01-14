package presentation.cli.onetime

import application.crawler.article.QiitaRawPropsArticleJsonCrawlerApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.onetime.AllQiitaRawPropsArticleJsonCrawlerCli
  */
object AllQiitaRawPropsArticleJsonCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaRawPropsArticleJsonCrawlerApplication].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
