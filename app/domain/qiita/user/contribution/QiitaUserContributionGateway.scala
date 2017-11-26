package domain.qiita.user.contribution

import domain.qiita.user.QiitaUserName

trait QiitaUserContributionGateway {
  def fetch(qiitaUserName: QiitaUserName): QiitaUserContribution
}
