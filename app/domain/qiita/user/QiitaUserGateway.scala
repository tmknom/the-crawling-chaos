package domain.qiita.user

trait QiitaUserGateway {
  def fetch(url: String): Seq[QiitaUser]
}
