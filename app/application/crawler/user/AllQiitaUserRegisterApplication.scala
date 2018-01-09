package application.crawler.user

import javax.inject.{Inject, Singleton}

import domain.qiita.user.json.QiitaRawInternalUserJsonRepository
import domain.qiita.user.{QiitaUserName, QiitaUserRepository}

@Singleton
final class AllQiitaUserRegisterApplication @Inject()(
    repository:                         QiitaUserRepository,
    qiitaRawInternalUserJsonRepository: QiitaRawInternalUserJsonRepository
) {

  def crawl(): Unit = {
    val qiitaUserNames = qiitaRawInternalUserJsonRepository.retrieveRecently()
    qiitaUserNames.foreach(register)
  }

  private def register(qiitaUserName: QiitaUserName): Unit = {
    val rawInternalUserJson = qiitaRawInternalUserJsonRepository.retrieve(qiitaUserName)
    // 永続化対象のデータを取り出し

    //永続化

  }
}
