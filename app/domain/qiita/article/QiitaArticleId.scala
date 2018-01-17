package domain.qiita.article

final case class QiitaArticleId(value: Int)

object QiitaArticleId {
  val undefined = QiitaArticleId(-1)
}
