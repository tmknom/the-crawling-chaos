package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionGateway}
import library.scalaj.ScalajHttpAdaptor

final class HttpQiitaUserContributionGateway extends QiitaUserContributionGateway {

  def fetch(qiitaUserName: QiitaUserName): QiitaUserContribution = {
    val response = ScalajHttpAdaptor.get(qiitaUserName.urlHovercardUsers)
    QiitaUserContributionParser(response).parse
  }
}
