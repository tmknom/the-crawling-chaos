package application.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.QiitaUserRepository

@Singleton
final class QiitaUserNameApplication @Inject()(
    repository: QiitaUserRepository
) {

  /**
    * 無効な Qiita ユーザの削除
    */
  def deleteUnavailable(): Unit = {
    val qiitaUsers = repository.retrieveUnavailable()
    qiitaUsers.foreach { qiitaUser =>
      repository.delete(qiitaUser.id)
    }
  }
}
