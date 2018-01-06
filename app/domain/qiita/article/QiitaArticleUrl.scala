package domain.qiita.article

final case class QiitaArticleUrl(value: String) {
  def hatenaApiUrl: String = {
    s"http://api.b.st-hatena.com/entry.count?url=$value"
  }
}
