package presentation.cli.onetime

import application.crawler.user.AllQiitaUserRegisterApplication
import library.task.Task
import play.api.{Application, Logger}

/**
  * 全 Qiita ユーザを登録
  *
  * run-main presentation.cli.onetime.AllQiitaUserRegisterCli
  */
object AllQiitaUserRegisterCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      app.injector.instanceOf[AllQiitaUserRegisterApplication].crawl()
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }
}
