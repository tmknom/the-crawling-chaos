package application.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.{QiitaArticleIdRepository, QiitaItemId}

@Singleton
final class QiitaItemIdApplication @Inject()(
    repository: QiitaArticleIdRepository
) {
  def delete(qiitaItemId: QiitaItemId): Unit = {
    repository.delete(qiitaItemId)
  }
}
