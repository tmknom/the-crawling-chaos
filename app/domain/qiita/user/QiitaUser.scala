package domain.qiita.user

final case class QiitaUser(id: QiitaUserId, name: QiitaUserName)

final case class QiitaUserName(value: String)
