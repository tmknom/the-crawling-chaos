package domain.qiita.user

import domain.Identifier

final case class QiitaUserName(value: String) extends Identifier[String] {
  def urlHovercardUsers: String = {
    s"https://qiita.com/api/internal/hovercard_users/$value"
  }

  def urlHatena: String = {
    s"http://b.hatena.ne.jp/bc/http://qiita.com/$value/"
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
