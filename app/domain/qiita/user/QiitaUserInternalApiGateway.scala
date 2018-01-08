package domain.qiita.user

import domain.qiita.user.json.RawInternalUserJson

trait QiitaUserInternalApiGateway {
  def fetch(qiitaUserName: QiitaUserName): RawInternalUserJson
}
