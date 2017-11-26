package domain.qiita.initial

final case class QiitaUserInitial(initial: Initial, page: Page) {
  def pageRange: Range = page.range

  def usersUrl(currentPage: Int): String = {
    s"https://qiita.com/users?char=${initial.value}&page=$currentPage"
  }
}

final case class Initial(value: Char)

final case class Page(value: Int) {
  def range: Range = {
    1 to value
  }
}
