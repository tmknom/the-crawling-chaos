package infrastructure.qiita.article.json

import javax.inject.Singleton

import domain.qiita.article._
import domain.qiita.article.json.{QiitaRawArticleJsonRepository, RawArticleJson}
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaRawArticleJsonRepository extends QiitaRawArticleJsonRepository {
  override def register(qiitaItemId: QiitaItemId, rawArticleJson: RawArticleJson)(implicit session: DBSession = AutoSession): Unit = {
    val itemId  = qiitaItemId.value
    val rawJson = rawArticleJson.value
    sql"""
          INSERT INTO raw_qiita_article_jsons (qiita_article_item_id, raw_json)
          VALUES ($itemId, $rawJson);
       """
      .update()
      .apply()

    () // 明示的に Unit を返す
  }

  override def retrieveAll()(implicit session: DBSession = AutoSession): List[(QiitaItemId, RawArticleJson)] = {
    sql"""
          SELECT item_id, raw_json FROM raw_qiita_article_jsons AS rqaj
          INNER JOIN qiita_articles AS qa ON rqaj.qiita_article_item_id = qa.item_id
          WHERE NOT EXISTS
          (SELECT 1 FROM qiita_article_contributions AS qac WHERE rqaj.qiita_article_item_id = qa.item_id)
          ORDER BY id ASC;
       """
      .map(map)
      .list()
      .apply()
  }

  private def map(rs: WrappedResultSet): (QiitaItemId, RawArticleJson) = {
    (QiitaItemId(rs.string("item_id")), RawArticleJson(rs.string("raw_json")))
  }
}
