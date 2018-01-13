package application.qiita.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.{QiitaUser, QiitaUserRepository}

@Singleton
class QiitaUserApplication @Inject()(
    repository: QiitaUserRepository
) {
  def list(): List[QiitaUser] = {
    repository.retrieveTop100()
  }
}
