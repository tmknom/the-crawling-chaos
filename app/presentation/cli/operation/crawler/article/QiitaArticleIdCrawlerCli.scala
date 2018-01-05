package presentation.cli.operation.crawler.article

import application.crawler.QiitaArticleListCrawlerApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.operation.crawler.article.QiitaArticleIdCrawlerCli
  */
object QiitaArticleIdCrawlerCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaArticleListCrawlerApplication].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
