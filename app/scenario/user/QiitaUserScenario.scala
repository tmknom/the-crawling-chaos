package scenario.user

import javax.inject.{Inject, Singleton}

import application.crawler.user.QiitaUserContributionCrawlerApplication
import application.qiita.user.QiitaUserNameApplication
import domain.qiita.user.{QiitaUser, QiitaUserRepository}

@Singleton
final class QiitaUserScenario @Inject()(
    qiitaUserNameApplication:                QiitaUserNameApplication,
    qiitaUserContributionCrawlerApplication: QiitaUserContributionCrawlerApplication,
    repository:                              QiitaUserRepository
) {

  /**
    * ユーザのいいね数をクロールし、無効判定されたユーザを削除
    */
  def deleteUnavailable(): Unit = {
    // ユーザのいいね数をクロール（無効なユーザはクロールに失敗する／ただし例外は飛ばない）
    val qiitaUsers: Seq[QiitaUser] = repository.retrieveRecently()
    qiitaUserContributionCrawlerApplication.crawl(qiitaUsers)
    // 無効判定されたユーザを削除
    qiitaUserNameApplication.deleteUnavailable()
  }
}
