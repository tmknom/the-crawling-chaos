package presentation.cli.operation

import application.content.article.QiitaArticleRankingApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.operation.QiitaArticleRankingCli
  */
object QiitaArticleRankingCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaArticleRankingApplication].createContribution()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
