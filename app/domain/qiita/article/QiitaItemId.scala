package domain.qiita.article

final case class QiitaItemId(value: String) {
  def apiUrl: String = {
    s"https://qiita.com/api/v2/items/$value"
  }

  def url: String = {
    s"https://qiita.com/items/$value"
  }
}

object QiitaItemId {
  private val PageMax: Int = 15000
  private val PageMin: Int = 1

  def pageRange: Range = {
    PageMin to PageMax
  }

  def pageMax: Int = {
    PageMax
  }
}
