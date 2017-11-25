package infrastructure.qiita.user

import domain.qiita.user.{QiitaUser, QiitaUserGateway}
import library.adaptor.DispatchAdaptor

final class HttpQiitaUserGateway extends QiitaUserGateway {
  override def fetch(url: String): Seq[QiitaUser] = {
    val response = DispatchAdaptor(url).request()
    QiitaUserParser(response).parse
  }
}
