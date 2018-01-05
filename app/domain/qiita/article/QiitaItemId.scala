package domain.qiita.article

final case class QiitaItemId(value: String) {
  def apiUrl: String = {
    s"https://qiita.com/api/v2/items/$value"
  }
}
