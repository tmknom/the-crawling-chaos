package application.crawler.user

import javax.inject.{Inject, Singleton}

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
import domain.crawler.Sleeper
import domain.qiita.user.{QiitaUserApiGateway, QiitaUserName, QiitaUserNameRepository}
import play.api.Logger

@Singleton
final class QiitaUserNameCrawlerApplication @Inject()(
    repository: QiitaUserNameRepository,
    gateway:    QiitaUserApiGateway
) {

  def crawl(): Unit = {
    try {
      crawlUntilDuplicated()
    } catch {
      // すでに登録済みの情報を登録しようとしたら、MySQLIntegrityConstraintViolationExceptionが飛ぶ
      // あまり行儀がよくないが、重複エラーが処理終了のタイイングと合致して都合がいいので、
      // 例外を飛ばしてそいつをキャッチして終了することにした
      case e: MySQLIntegrityConstraintViolationException => {
        Logger.info(s"${this.getClass.getSimpleName} crawled end because ${e.getMessage}")
      }
    }
  }

  private def crawlUntilDuplicated(): Unit = {
    QiitaUserName.pageRange.foreach { currentPage =>
      val qiitaUserNames = gateway.fetch(currentPage)
      qiitaUserNames.foreach(repository.register)

      Logger.info(s"${this.getClass.getSimpleName} crawled ($currentPage / ${QiitaUserName.pageMax})")
      Sleeper.sleep()
    }
  }
}
