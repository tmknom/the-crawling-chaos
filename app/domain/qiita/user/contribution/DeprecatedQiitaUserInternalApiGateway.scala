package domain.qiita.user.contribution

import domain.qiita.user.DeprecatedQiitaUser
import domain.qiita.user.summary.QiitaUserSummary

trait DeprecatedQiitaUserInternalApiGateway {
  def fetch(qiitaUser: DeprecatedQiitaUser): QiitaUserSummary
}
