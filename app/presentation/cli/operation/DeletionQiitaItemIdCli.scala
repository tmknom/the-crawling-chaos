package presentation.cli.operation

import application.qiita.article.QiitaItemIdApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.operation.DeletionQiitaItemIdCli "xxxx, yyyy"
  */
object DeletionQiitaItemIdCli extends App with Task {
  private val qiitaItemIds: String = args(0)

  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaItemIdApplication].delete(qiitaItemIds)
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
