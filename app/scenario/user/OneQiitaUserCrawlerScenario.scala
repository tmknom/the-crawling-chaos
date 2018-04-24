package scenario.user

import javax.inject.{Inject, Singleton}

import application.crawler.user.QiitaRawInternalUserJsonCrawlerApplication
import application.qiita.user.QiitaUserRegisterApplication
import domain.qiita.user.QiitaUserName

@Singleton
final class OneQiitaUserCrawlerScenario @Inject()(
    qiitaRawInternalUserJsonCrawlerApplication: QiitaRawInternalUserJsonCrawlerApplication,
    qiitaUserRegisterApplication:               QiitaUserRegisterApplication
) {

  /**
    * 指定したQiitaユーザの情報（評価含む）をクロール
    */
  def crawl(qiitaUserName: QiitaUserName): Unit = {
    // QiitaユーザのJSONをクロール
    qiitaRawInternalUserJsonCrawlerApplication.crawlOne(qiitaUserName)
    // Qiitaユーザの情報情報（評価含む）を永続化
    qiitaUserRegisterApplication.registerOne(qiitaUserName)
  }
}
