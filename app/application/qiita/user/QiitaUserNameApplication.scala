package application.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.DeprecatedQiitaUserRepository

@Singleton
final class QiitaUserNameApplication @Inject()(
    repository: DeprecatedQiitaUserRepository
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
