package infrastructure.qiita.article.json

import javax.inject.Singleton

import domain.qiita.article.QiitaItemId
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
}
