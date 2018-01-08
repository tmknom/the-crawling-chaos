package presentation.cli.batch.monthly

import library.task.Task
import play.api.{Application, Logger}
import scenario.user.QiitaUserScenario

/**
  * run-main presentation.cli.batch.monthly.DeletionUnavailableQiitaUserNameCli
  */
object DeletionUnavailableQiitaUserNameCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserScenario].deleteUnavailable()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
