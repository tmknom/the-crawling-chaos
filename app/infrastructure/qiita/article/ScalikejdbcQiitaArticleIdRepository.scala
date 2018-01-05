package infrastructure.qiita.article

import javax.inject.Singleton

import domain.qiita.article.{QiitaArticleIdRepository, QiitaItemId}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleIdRepository extends QiitaArticleIdRepository {
  override def register(qiitaItemId: QiitaItemId)(implicit session: DBSession = AutoSession): Unit = {
    val itemId = qiitaItemId.value

    sql"""
          INSERT INTO qiita_article_ids (item_id)
          VALUES ($itemId);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }
}
