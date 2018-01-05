package domain.qiita.article

trait QiitaArticleIdGateway {
  def fetch(pageNumber: Int): List[QiitaArticle]
}
