package domain.qiita.article

final case class QiitaArticleUrl(value: String) {
  def hatenaApiUrl: String = {
    s"http://api.b.st-hatena.com/entry.count?url=$value"
  }

  def facebookApiUrl: String = {
    s"https://www.facebook.com/plugins/like.php?href=$value"
  }

  def pocketApiUrl: String = {
    s"https://widgets.getpocket.com/v1/button?label=pocket&count=horizontal&v=1&url=$value&src=$value"
  }
}
