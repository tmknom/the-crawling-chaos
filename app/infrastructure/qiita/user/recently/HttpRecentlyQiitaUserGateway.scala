package infrastructure.qiita.user.recently

import javax.inject.{Inject, Singleton}

import domain.qiita.user.QiitaUserName
import domain.qiita.user.recently.RecentlyQiitaUserGateway
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpRecentlyQiitaUserGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends RecentlyQiitaUserGateway {
  val BaseUrl = "https://qiita.com/api/v2/users?per_page=100&page="

  override def fetch(page: Int): Seq[QiitaUserName] = {
    val response = scalajHttpAdaptor.get(BaseUrl + page.toString)
    RecentlyQiitaUserParser(response).parse
  }
}
