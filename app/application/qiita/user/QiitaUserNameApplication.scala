package application.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.QiitaUserNameRepository

@Singleton
final class QiitaUserNameApplication @Inject()(
    repository: QiitaUserNameRepository
) {

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
