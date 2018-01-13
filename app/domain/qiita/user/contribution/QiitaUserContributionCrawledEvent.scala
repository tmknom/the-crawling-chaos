package domain.qiita.user.contribution

import domain.qiita.user.QiitaUserName
import domain.qiita.user.json.CrawledDateTime

final case class QiitaUserContributionCrawledEvent(
    qiitaUserName:         QiitaUserName,
    qiitaUserContribution: QiitaUserContribution,
    articlesCount:         ArticlesCount,
    crawledDateTime:       CrawledDateTime
)
