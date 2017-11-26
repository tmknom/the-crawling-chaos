package domain.qiita.initial

trait QiitaUserInitialGateway {
  def fetch(): Seq[QiitaUserInitial]
}
