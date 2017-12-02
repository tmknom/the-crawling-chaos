package infrastructure.qiita.user.contribution

import javax.inject.Inject

import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.{QiitaUserContribution, QiitaUserContributionGateway}
import library.scalaj.ScalajHttpAdaptor

final class HttpQiitaUserContributionGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends QiitaUserContributionGateway {

  def fetch(qiitaUserName: QiitaUserName): QiitaUserContribution = {
    val response = scalajHttpAdaptor.get(qiitaUserName.urlHovercardUsers)
    QiitaUserContributionParser(response).parse
  }
}
