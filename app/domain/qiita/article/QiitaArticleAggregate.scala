package domain.qiita.article

import domain.qiita.article.contribution.QiitaArticleContribution

final case class QiitaArticleAggregate(article: QiitaArticle, contribution: QiitaArticleContribution)
