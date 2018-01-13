package domain.qiita.user.contribution

import domain.qiita.user.QiitaUserName
import domain.qiita.user.event.EventDateTime

final case class QiitaUserContributionCrawledEvent(
    qiitaUserName:         QiitaUserName,
    qiitaUserContribution: QiitaUserContribution,
    articlesCount:         ArticlesCount,
    eventDateTime:         EventDateTime
)
