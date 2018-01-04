package domain.qiita.user.contribution

import domain.qiita.user.QiitaUser
import domain.qiita.user.summary.QiitaUserSummary

trait QiitaUserContributionGateway {
  def fetch(qiitaUser: QiitaUser): QiitaUserSummary
}
