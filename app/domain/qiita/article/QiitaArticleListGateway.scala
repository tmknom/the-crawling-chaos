package domain.qiita.article

trait QiitaArticleListGateway {
  def fetch(pageNumber: Int): List[QiitaArticle]
}
