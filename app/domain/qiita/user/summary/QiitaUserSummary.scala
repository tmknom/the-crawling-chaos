package domain.qiita.user.summary

import domain.qiita.user.contribution.QiitaUserContribution
import domain.qiita.user.{QiitaUserId, QiitaUserName}

final case class QiitaUserSummary(id: QiitaUserId, name: QiitaUserName, contribution: QiitaUserContribution)
