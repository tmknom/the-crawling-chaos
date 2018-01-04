package domain.qiita.user.ranking

import domain.qiita.user.contribution.QiitaUserContribution
import domain.qiita.user.{QiitaUser, QiitaUserId, QiitaUserName, RegisteredDateTime}

final case class QiitaUserRanking(qiitaUserId: QiitaUserId, name: QiitaUserName, contribution: QiitaUserContribution) {
  def toQiitaUser: QiitaUser = {
    QiitaUser(qiitaUserId, name, RegisteredDateTime.now())
  }
}
