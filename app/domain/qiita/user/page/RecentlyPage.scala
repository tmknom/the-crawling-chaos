package domain.qiita.user.page

object RecentlyPage {
  val PageMax: Int = 100
  val PageMin: Int = 1

  def range: Range = {
    PageMin to PageMax
  }
}
