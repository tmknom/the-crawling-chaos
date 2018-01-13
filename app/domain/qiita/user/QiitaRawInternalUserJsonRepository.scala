package domain.qiita.user

import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaRawInternalUserJsonRepository {
  def register(qiitaUserName: QiitaUserName, rawInternalUserJson: RawInternalUserJson, crawledDateTime: CrawledDateTime)(implicit session: DBSession =
                                                                                                                           AutoSession): Unit

  def retrieve(qiitaUserName: QiitaUserName)(implicit session: DBSession = AutoSession): Option[RawInternalUserJson]

  def retrieveRecently()(implicit session: DBSession = AutoSession): List[QiitaUserName]
}
