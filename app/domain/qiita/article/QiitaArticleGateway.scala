package domain.qiita.article

trait QiitaArticleGateway {
  def fetch(qiitaItemId: QiitaItemId): QiitaArticle
}
