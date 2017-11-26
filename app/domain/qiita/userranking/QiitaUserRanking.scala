package domain.qiita.userranking

import domain.qiita.user.QiitaUserId

final case class QiitaUserRanking(qiitaUserId: QiitaUserId, name: QiitaUserRankingName, contribution: QiitaUserRankingContribution)

final case class QiitaUserRankingName(value: String)

final case class QiitaUserRankingContribution(value: Int)
