package domain.qiita.user.contribution

import domain.qiita.user.{CrawledDateTime, QiitaUserName}

final case class QiitaUserContributionCrawledEvent(
    qiitaUserName:   QiitaUserName,
    contribution:    Contribution,
    articlesCount:   ArticlesCount,
    crawledDateTime: CrawledDateTime
)
