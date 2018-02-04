package presentation.cli.onetime

import application.crawler.article.QiitaArticleRegisterApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.onetime.AllQiitaArticleRegisterCli
  */
object AllQiitaArticleRegisterCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaArticleRegisterApplication].register()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
