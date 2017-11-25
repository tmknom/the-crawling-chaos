package domain.qiita

final case class QiitaUserInitial(initial: Initial, page: Page) {
  def pageRange: Range = page.range
}

final case class Initial(value: Char)

final case class Page(value: Int) {
  def range: Range = {
    1 to value
  }
}
