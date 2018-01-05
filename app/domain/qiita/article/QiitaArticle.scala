package domain.qiita.article

import domain.qiita.user.QiitaUserName

final case class QiitaArticle(
    id:             QiitaArticleId,
    itemId:         QiitaItemId,
    title:          QiitaArticleTitle,
    url:            QiitaArticleUrl,
    postedDateTime: QiitaArticlePostedDateTime,
    userName:       QiitaUserName
) {}
