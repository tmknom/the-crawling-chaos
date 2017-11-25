package infrastructure.qiita

import domain.qiita.{QiitaUserInitial, QiitaUserInitialRepository}
import scalikejdbc._

final class ScalikejdbcQiitaUserInitialRepository extends QiitaUserInitialRepository {
  override def register(qiitaUserInitial: QiitaUserInitial)(implicit session: DBSession = AutoSession): Unit = {
    val initial = qiitaUserInitial.initial.value.toString
    val page    = qiitaUserInitial.page.value

    sql"INSERT INTO qiita_user_initials (initial, page) VALUES ($initial, $page);".update.apply()
  }
}
