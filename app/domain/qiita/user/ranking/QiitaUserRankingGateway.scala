package domain.qiita.user.ranking

trait QiitaUserRankingGateway {
  def fetch(page: Int): Seq[QiitaUserRanking]
}
