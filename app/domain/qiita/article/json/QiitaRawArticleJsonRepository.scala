package domain.qiita.article.json

import domain.qiita.article.QiitaItemId
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaRawArticleJsonRepository {
  def register(qiitaItemId: QiitaItemId, rawArticleJson: RawArticleJson)(implicit session: DBSession = AutoSession): Unit
}
