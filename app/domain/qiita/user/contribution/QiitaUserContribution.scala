package domain.qiita.user.contribution

import domain.qiita.article.contribution.HatenaCount

final case class QiitaUserContribution(
    contribution:  Contribution,
    articlesCount: ArticlesCount,
    hatenaCount:   HatenaCount
) {

}
