package domain.qiita.user.ranking

import domain.qiita.user.contribution.QiitaUserContribution
import domain.qiita.user.{DeprecatedQiitaUser, QiitaUserId, QiitaUserName, RegisteredDateTime}

final case class QiitaUserRanking(qiitaUserId: QiitaUserId, name: QiitaUserName, contribution: QiitaUserContribution) {
  def toQiitaUser: DeprecatedQiitaUser = {
    DeprecatedQiitaUser(qiitaUserId, name, RegisteredDateTime.now())
  }
}
