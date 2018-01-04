package domain.qiita.user.contribution

import domain.qiita.user.QiitaUser

trait QiitaUserContributionGateway {
  def fetch(qiitaUser: QiitaUser): QiitaUserContribution
}
