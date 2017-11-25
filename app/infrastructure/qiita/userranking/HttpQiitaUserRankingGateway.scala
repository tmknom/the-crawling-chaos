package infrastructure.qiita.userranking

import domain.qiita.userranking.{QiitaUserRanking, QiitaUserRankingGateway}

final class HttpQiitaUserRankingGateway extends QiitaUserRankingGateway {
  override def fetch(page: Int): Seq[QiitaUserRanking] = {
    Seq.empty[QiitaUserRanking]
  }
}
