package scenario.user

import javax.inject.{Inject, Singleton}

import application.crawler.user.QiitaRawInternalUserJsonCrawlerApplication
import application.qiita.user.QiitaUserRegisterApplication

@Singleton
final class QiitaUserCrawlerScenario @Inject()(
    qiitaRawInternalUserJsonCrawlerApplication: QiitaRawInternalUserJsonCrawlerApplication,
    qiitaUserRegisterApplication:               QiitaUserRegisterApplication
) {

  /**
    * 最近登録したQiitaユーザの情報（評価含む）をクロール
    */
  def crawl(): Unit = {
    // QiitaユーザのJSONをクロール
    qiitaRawInternalUserJsonCrawlerApplication.crawl()
    // Qiitaユーザの情報情報（評価含む）を永続化
    qiitaUserRegisterApplication.registerRecently()
  }
}
