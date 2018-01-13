package domain.qiita.user

import domain.qiita.user.contribution.QiitaUserContribution

final case class QiitaUser(profile: QiitaUserProfile, qiitaUserContribution: QiitaUserContribution)
