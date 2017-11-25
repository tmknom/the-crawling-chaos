package infrastructure.qiita.userranking

import domain.qiita.userranking.{QiitaUserRanking, QiitaUserRankingGateway}
import library.adaptor.DispatchAdaptor

final class HttpQiitaUserRankingGateway extends QiitaUserRankingGateway {
  override def fetch(page: Int): Seq[QiitaUserRanking] = {
    val url      = s"https://qiita-user-ranking.herokuapp.com/?page=${page.toString}"
    val response = DispatchAdaptor(url).request()
    QiitaUserRankingParser(response).parse
  }
}
