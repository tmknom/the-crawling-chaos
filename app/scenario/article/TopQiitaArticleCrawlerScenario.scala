package scenario.article

import javax.inject.{Inject, Singleton}

import application.crawler.article.{QiitaArticleContributionCrawlerApplication, QiitaRawPropsArticleJsonCrawlerApplication}

@Singleton
final class TopQiitaArticleCrawlerScenario @Inject()(
    qiitaRawPropsArticleJsonCrawlerApplication: QiitaRawPropsArticleJsonCrawlerApplication,
    qiitaArticleContributionCrawlerApplication: QiitaArticleContributionCrawlerApplication
) {

  /**
    * トップQiita記事の評価をクロール
    */
  def crawl(): Unit = {
    // Qiita記事のJSONをクロール
    qiitaRawPropsArticleJsonCrawlerApplication.crawlTopArticle()
    // Qiita記事の評価を永続化
    qiitaArticleContributionCrawlerApplication.crawlTopArticle()
  }
}
