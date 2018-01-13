package domain.qiita.user.ranking

import domain.qiita.user.contribution.Contribution
import domain.qiita.user.{QiitaUserId, QiitaUserName}

final case class QiitaUserRanking(qiitaUserId: QiitaUserId, name: QiitaUserName, contribution: Contribution)
