package domain.qiita

trait QiitaUserInitialGateway {
  def fetch(): Seq[QiitaUserInitial]
}
