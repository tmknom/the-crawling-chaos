package domain.qiita

final case class QiitaUserInitial(initial: Initial, page: Page)

final case class Initial(value: Char)

final case class Page(value: Int)
