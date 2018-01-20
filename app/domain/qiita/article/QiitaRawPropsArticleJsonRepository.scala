package domain.qiita.article

import domain.qiita.article.json.RawPropsArticleJson
import domain.qiita.user.CrawledDateTime
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaRawPropsArticleJsonRepository {
  def register(itemId: QiitaItemId, rawJson: RawPropsArticleJson, crawled: CrawledDateTime)(implicit session: DBSession = AutoSession): Unit

  def retrieve(itemId: QiitaItemId)(implicit session: DBSession = AutoSession): Option[RawPropsArticleJson]
}
