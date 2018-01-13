package domain.qiita.user

final case class QiitaUserName(value: String) {
  def urlHovercardUsers: String = {
    s"https://qiita.com/api/internal/hovercard_users/$value"
  }
}

object QiitaUserName {
  private val PageMax: Int = 100
  private val PageMin: Int = 1

  def pageRange: Range = {
    PageMin to PageMax
  }

  def pageMax: Int = {
    PageMax
  }
}
