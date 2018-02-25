package domain.qiita.user.contribution

import domain.qiita.article.contribution.HatenaCount
import domain.qiita.user.QiitaUserName

trait HatenaApiGateway {
  def fetch(qiitaUserName: QiitaUserName): HatenaCount
}
