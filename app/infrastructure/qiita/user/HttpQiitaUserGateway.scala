package infrastructure.qiita.user

import domain.qiita.user.{QiitaUser, QiitaUserGateway}
import library.scalaj.ScalajHttpAdaptor

final class HttpQiitaUserGateway extends QiitaUserGateway {
  override def fetch(url: String): Seq[QiitaUser] = {
    val response = ScalajHttpAdaptor.get(url)
    QiitaUserParser(response).parse
  }
}
