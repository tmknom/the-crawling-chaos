package domain.qiita.user.recently

import domain.qiita.user.QiitaUserName

trait RecentlyQiitaUserGateway {
  def fetch(page: Int): Seq[QiitaUserName]
}
