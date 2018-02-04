package presentation.cli.onetime

import application.crawler.article.QiitaArticleMarkdownRegisterApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.onetime.AllQiitaArticleMarkdownRegisterCli
  */
object AllQiitaArticleMarkdownRegisterCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaArticleMarkdownRegisterApplication].register()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
