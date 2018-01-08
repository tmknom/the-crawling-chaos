package infrastructure.qiita.initial

import javax.inject.Singleton

import domain.qiita.initial.{Initial, Page, QiitaUserInitial, QiitaUserInitialRepository}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaUserInitialRepository extends QiitaUserInitialRepository {
  override def register(qiitaUserInitial: QiitaUserInitial)(implicit session: DBSession = AutoSession): Unit = {
    val initial = qiitaUserInitial.initial.value.toString
    val page    = qiitaUserInitial.page.value

    sql"INSERT INTO deprecated_qiita_user_initials (initial, page) VALUES ($initial, $page);".update.apply()

    () // 明示的に Unit を返す
  }

  override def retrieveAll()(implicit session: DBSession = AutoSession): Seq[QiitaUserInitial] = {
    sql"SELECT * FROM deprecated_qiita_user_initials ORDER BY id ASC;".map { rs =>
      QiitaUserInitial(Initial(rs.string("initial").charAt(0)), Page(rs.int("page")))
    }.list().apply()
  }
}
