package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionGateway}

import scalaj.http.Http

final class HttpQiitaUserContributionGateway extends QiitaUserContributionGateway {

  def fetch(qiitaUserName: QiitaUserName): QiitaUserContribution = {
    val response: String = Http(qiitaUserName.urlHovercardUsers).asString.throwError.body
    QiitaUserContributionParser(response).parse
  }
}
