package application.qiita.article

import javax.inject.{Inject, Singleton}

import domain.qiita.article.{QiitaArticleIdRepository, QiitaItemId}

@Singleton
final class QiitaItemIdApplication @Inject()(
    repository: QiitaArticleIdRepository
) {
  def delete(stringQiitaItemIds: String): Unit = {
    stringQiitaItemIds.split(",").foreach { stringQiitaItemId =>
      val qiitaItemId = QiitaItemId(stringQiitaItemId.trim)
      repository.delete(qiitaItemId)
    }
  }
}
