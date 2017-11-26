package infrastructure.qiita

import domain.qiita.{Initial, Page, QiitaUserInitial, QiitaUserInitialRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments"))
final class ScalikejdbcQiitaUserInitialRepository extends QiitaUserInitialRepository {
  override def register(qiitaUserInitial: QiitaUserInitial)(implicit session: DBSession = AutoSession): Unit = {
    val initial = qiitaUserInitial.initial.value.toString
    val page    = qiitaUserInitial.page.value

    sql"INSERT INTO qiita_user_initials (initial, page) VALUES ($initial, $page);".update.apply()
  }

  override def retrieveAll()(implicit session: DBSession = AutoSession): Seq[QiitaUserInitial] = {
    sql"SELECT * FROM qiita_user_initials ORDER BY id ASC;".map { rs =>
      QiitaUserInitial(Initial(rs.string("initial").charAt(0)), Page(rs.int("page")))
    }.list().apply()
  }
}
