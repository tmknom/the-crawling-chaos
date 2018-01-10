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
    val optionValue = qiitaRawInternalUserJsonRepository.retrieve(qiitaUserName)
    val rawInternalUserJson = optionValue match {
      case Some(v) => v
      case None    => throw new RuntimeException(s"ココに来たらバグなので雑に例外をスロー ${qiitaUserName.value}")
    }
    repository.register(rawInternalUserJson.toQiitaUser)
  }
}
