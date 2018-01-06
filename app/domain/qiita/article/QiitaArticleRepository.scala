package domain.qiita.article

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaArticleRepository {
  def register(qiitaArticle: QiitaArticle)(implicit session: DBSession = AutoSession): Unit

  def retrieve(qiitaItemId: QiitaItemId)(implicit session: DBSession = AutoSession): QiitaArticle
}
