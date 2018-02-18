package application.qiita.user

import javax.inject.{Inject, Singleton}

import domain.crawler.Crawler
import domain.qiita.user.{QiitaUserName, QiitaUserNameRepository}

@Singleton
final class QiitaUserNameApplication @Inject()(
    repository: QiitaUserNameRepository
) extends Crawler {

  /**
    * 指定した Qiita ユーザを登録
    */
  def register(stringQiitaUserNames: String): Unit = {
    val items = stringQiitaUserNames
      .split(",")
      .map { item =>
        QiitaUserName(item.trim)
      }
      .toList
    withoutSleepLoop[QiitaUserName](items)(repository.register)
  }

  /**
    * 無効な Qiita ユーザの削除
    */
  def deleteUnavailable(): Unit = {
    val qiitaUserNames = repository.retrieveUnavailable()
    qiitaUserNames.foreach { qiitaUserName =>
      repository.delete(qiitaUserName)
    }
  }
}
