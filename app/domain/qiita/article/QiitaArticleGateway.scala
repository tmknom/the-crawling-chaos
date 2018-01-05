package domain.qiita.article

trait QiitaArticleGateway {
  def fetch(pageNumber: Int): List[QiitaArticle]
}
