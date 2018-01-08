package domain.qiita.user

trait QiitaUserNameGateway {
  def fetch(url: String): Seq[QiitaUserName]
}
