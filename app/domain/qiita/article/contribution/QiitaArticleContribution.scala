package domain.qiita.article.contribution

import domain.qiita.article.QiitaItemId
import domain.qiita.user.CrawledDateTime

/**
  * Qiita 記事の評価
  */
final case class QiitaArticleContribution(
    likesCount:    LikesCount,
    commentsCount: CommentsCount,
    hatenaCount:   HatenaCount,
    facebookCount: FacebookCount,
    pocketCount:   PocketCount
) {

  def toCrawledEvent(qiitaItemId: QiitaItemId): QiitaArticleContributionCrawledEvent = {
    QiitaArticleContributionCrawledEvent(
      qiitaItemId,
      this,
      CrawledDateTime.now()
    )
  }
}
