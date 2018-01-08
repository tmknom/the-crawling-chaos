package domain.qiita.user.contribution

import domain.qiita.user.QiitaUser
import domain.qiita.user.summary.QiitaUserSummary

trait DeprecatedQiitaUserInternalApiGateway {
  def fetch(qiitaUser: QiitaUser): QiitaUserSummary
}
