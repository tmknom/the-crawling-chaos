package domain.qiita.user

trait QiitaUserGateway {
  def fetch(): Seq[QiitaUser]
}
