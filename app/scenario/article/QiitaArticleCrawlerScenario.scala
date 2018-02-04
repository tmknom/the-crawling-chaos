package scenario.article

import javax.inject.{Inject, Singleton}

import application.crawler.article.{QiitaArticleContributionCrawlerApplication, QiitaArticleRegisterApplication, QiitaRawPropsArticleJsonCrawlerApplication}

@Singleton
final class QiitaArticleCrawlerScenario @Inject()(
    qiitaRawPropsArticleJsonCrawlerApplication: QiitaRawPropsArticleJsonCrawlerApplication,
    qiitaArticleRegisterApplication:            QiitaArticleRegisterApplication,
    qiitaArticleContributionCrawlerApplication: QiitaArticleContributionCrawlerApplication
) {

  /**
    * 最近投稿されたQiita記事の情報（評価含む）をクロール
    */
  def crawl(): Unit = {
    // Qiita記事のJSONをクロール
    qiitaRawPropsArticleJsonCrawlerApplication.crawl()
    // Qiita記事の情報情報を永続化
    qiitaArticleRegisterApplication.crawl()
    // Qiita記事の評価を永続化
    qiitaArticleContributionCrawlerApplication.crawl()
  }
}
