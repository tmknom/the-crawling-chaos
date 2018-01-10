package domain.qiita.user.event

import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}

final case class QiitaUserContributionCrawledEvent(
    qiitaUserName:         QiitaUserName,
    qiitaUserContribution: QiitaUserContribution,
    articlesCount:         ArticlesCount,
    eventDateTime:         EventDateTime
)
