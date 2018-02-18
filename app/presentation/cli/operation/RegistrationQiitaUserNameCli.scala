package presentation.cli.operation

import application.qiita.user.QiitaUserNameApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.operation.RegistrationQiitaUserNameCli "xxxx, yyyy"
  */
object RegistrationQiitaUserNameCli extends App with Task {
  private val qiitaUserNames: String = args(0)

  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaUserNameApplication].register(qiitaUserNames)
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
