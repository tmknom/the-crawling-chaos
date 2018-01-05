package domain.qiita.article.page

object QiitaArticlePage {
  val PageMax: Int = 1
  val PageMin: Int = 1

  def range: Range = {
    PageMin to PageMax
  }
}
