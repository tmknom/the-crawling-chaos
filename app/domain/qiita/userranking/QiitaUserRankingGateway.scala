package domain.qiita.userranking

trait QiitaUserRankingGateway {
  def fetch(page: Int): Seq[QiitaUserRanking]
}
