package domain.qiita.article

import domain.qiita.article.json.RawArticleJson
import domain.qiita.user.CrawledDateTime
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaRawPropsArticleJsonRepository {
  def register(itemId: QiitaItemId, rawJson: RawArticleJson, crawled: CrawledDateTime)(implicit session: DBSession = AutoSession): Unit

  def retrieve(itemId: QiitaItemId)(implicit session: DBSession = AutoSession): Option[RawArticleJson]
}
