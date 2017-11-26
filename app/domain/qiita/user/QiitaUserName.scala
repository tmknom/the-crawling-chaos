package domain.qiita.user

final case class QiitaUserName(value: String) {
  def urlHovercardUsers: String = {
    s"https://qiita.com/api/internal/hovercard_users/$value"
  }
}
