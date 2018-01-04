package domain.qiita.user.summary

import domain.qiita.user.contribution.{ArticlesCount, QiitaUserContribution}
import domain.qiita.user.{QiitaUserId, QiitaUserName}

final case class QiitaUserSummary(id: QiitaUserId, name: QiitaUserName, contribution: QiitaUserContribution, articlesCount: ArticlesCount)
