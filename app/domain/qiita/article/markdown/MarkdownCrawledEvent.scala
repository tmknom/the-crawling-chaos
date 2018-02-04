package domain.qiita.article.markdown

import domain.qiita.article.QiitaItemId
import domain.qiita.user.CrawledDateTime

final case class MarkdownCrawledEvent(
    qiitaItemId:     QiitaItemId,
    markdown:        Markdown,
    crawledDateTime: CrawledDateTime
)
