package domain.qiita.article.contribution

import domain.qiita.article.QiitaArticleUrl

trait FacebookGateway {
  def fetch(qiitaArticleUrl: QiitaArticleUrl): FacebookCount
}
