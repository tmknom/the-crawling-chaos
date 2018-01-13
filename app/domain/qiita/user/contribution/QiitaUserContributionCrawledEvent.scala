package domain.qiita.user.contribution

import domain.qiita.user.{CrawledDateTime, QiitaUserName}

final case class QiitaUserContributionCrawledEvent(
    qiitaUserName:         QiitaUserName,
    qiitaUserContribution: QiitaUserContribution,
    articlesCount:         ArticlesCount,
    crawledDateTime:       CrawledDateTime
)
