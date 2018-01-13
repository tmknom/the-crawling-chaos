package domain.qiita.user.ranking

import domain.qiita.user.contribution.QiitaUserContribution
import domain.qiita.user.{QiitaUserId, QiitaUserName}

final case class QiitaUserRanking(qiitaUserId: QiitaUserId, name: QiitaUserName, contribution: QiitaUserContribution)
