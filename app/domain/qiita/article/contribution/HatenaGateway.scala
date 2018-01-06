package domain.qiita.article.contribution

import domain.qiita.article.QiitaArticleUrl

trait HatenaGateway {
  def fetch(qiitaArticleUrl: QiitaArticleUrl): HatenaCount
}
