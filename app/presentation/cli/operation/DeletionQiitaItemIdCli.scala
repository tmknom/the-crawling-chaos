package presentation.cli.operation

import application.qiita.article.QiitaItemIdApplication
import domain.qiita.article.QiitaItemId
import library.task.Task
import play.api.{Application, Logger}

/**
  * run-main presentation.cli.operation.DeletionQiitaItemIdCli xxxx
  */
object DeletionQiitaItemIdCli extends App with Task {
  private val qiitaItemId = QiitaItemId(args(0))

  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[QiitaItemIdApplication].delete(qiitaItemId)
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
