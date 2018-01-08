package domain.qiita.user.initial

final case class Initial(value: Char) {
  def usersUrl(currentPage: Int): String = {
    s"https://qiita.com/users?char=$value&page=$currentPage"
  }
}
