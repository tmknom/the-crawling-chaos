package domain.qiita.user

trait QiitaUserApiGateway {
  def fetch(page: Int): Seq[QiitaUserName]
}
