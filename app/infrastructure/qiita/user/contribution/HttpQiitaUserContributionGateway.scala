package infrastructure.qiita.user.contribution

import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionGateway}
import library.adaptor.DispatchAdaptor

final class HttpQiitaUserContributionGateway extends QiitaUserContributionGateway {

  def fetch(qiitaUserName: QiitaUserName): QiitaUserContribution = {
    val response = DispatchAdaptor(qiitaUserName.urlHovercardUsers).request()
    QiitaUserContributionParser(response).parse
  }
}
