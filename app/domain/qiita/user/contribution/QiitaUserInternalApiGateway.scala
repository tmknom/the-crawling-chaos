package domain.qiita.user.contribution

import domain.qiita.user.QiitaUser
import domain.qiita.user.summary.QiitaUserSummary

trait QiitaUserInternalApiGateway {
  def fetch(qiitaUser: QiitaUser): QiitaUserSummary
}
