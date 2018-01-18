package domain.qiita.article.contribution

import domain.qiita.article.QiitaItemId
import domain.qiita.user.CrawledDateTime

final case class QiitaArticleContributionCrawledEvent(
    qiitaItemId:              QiitaItemId,
    qiitaArticleContribution: QiitaArticleContribution,
    crawledDateTime:          CrawledDateTime
)
