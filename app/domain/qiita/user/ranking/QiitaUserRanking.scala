package domain.qiita.user.ranking

import domain.qiita.user.QiitaUserName
import domain.qiita.user.contribution.Contribution

final case class QiitaUserRanking(name: QiitaUserName, contribution: Contribution)
