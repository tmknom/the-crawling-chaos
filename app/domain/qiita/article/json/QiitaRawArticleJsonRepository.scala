package domain.qiita.article.json

import domain.qiita.article._
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaRawArticleJsonRepository {
  def register(qiitaItemId: QiitaItemId, rawArticleJson: RawArticleJson)(implicit session: DBSession = AutoSession): Unit

  def retrieveAll()(implicit session: DBSession = AutoSession): List[(QiitaItemId, RawArticleJson)]
}
