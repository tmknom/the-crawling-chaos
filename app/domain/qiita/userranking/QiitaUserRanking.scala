package domain.qiita.userranking

import domain.qiita.user.{QiitaUserId, QiitaUserName}

final case class QiitaUserRanking(qiitaUserId: QiitaUserId, name: QiitaUserName, contribution: QiitaUserRankingContribution) {
  def urlHovercardUsers: String = name.urlHovercardUsers
}

final case class QiitaUserRankingContribution(value: Int)
