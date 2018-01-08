package domain.qiita.user.json

import domain.qiita.user.QiitaUserName
import scalikejdbc.{AutoSession, DBSession}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
trait QiitaRawInternalUserJsonRepository {
  def register(qiitaUserName: QiitaUserName, rawInternalUserJson: RawInternalUserJson)(implicit session: DBSession = AutoSession): Unit
}
