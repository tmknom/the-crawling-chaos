package domain.qiita.user

trait QiitaUserInternalApiGateway {
  def fetch(qiitaUserName: QiitaUserName): RawInternalUserJson
}
