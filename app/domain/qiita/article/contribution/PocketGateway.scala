package domain.qiita.article.contribution

import domain.qiita.article.QiitaArticleUrl

trait PocketGateway {
  def fetch(qiitaArticleUrl: QiitaArticleUrl): PocketCount
}
