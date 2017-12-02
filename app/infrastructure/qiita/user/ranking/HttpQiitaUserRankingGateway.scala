package infrastructure.qiita.user.ranking

import javax.inject.Inject

import domain.qiita.user.ranking.{QiitaUserRanking, QiitaUserRankingGateway}
import library.scalaj.ScalajHttpAdaptor

final class HttpQiitaUserRankingGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaUserRankingGateway {
  override def fetch(page: Int): Seq[QiitaUserRanking] = {
    val url      = s"https://qiita-user-ranking.herokuapp.com/?page=${page.toString}"
    val response = scalajHttpAdaptor.get(url)
    QiitaUserRankingParser(response).parse
  }
}
