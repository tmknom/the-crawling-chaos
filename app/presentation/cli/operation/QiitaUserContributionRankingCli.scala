package presentation.cli.operation

import application.content.QiitaUserContributionRankingApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.operation.QiitaUserContributionRankingCli
  */
object QiitaUserContributionRankingCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserContributionRankingApplication].create()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
