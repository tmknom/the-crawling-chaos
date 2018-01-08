package presentation.cli.batch.monthly

import application.qiita.user.QiitaUserNameApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.batch.monthly.DeletionUnavailableQiitaUserNameCli
  */
object DeletionUnavailableQiitaUserNameCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserNameApplication].deleteUnavailable()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
