package infrastructure.qiita.article

import javax.inject.Singleton

import domain.qiita.article._
import domain.qiita.article.contribution._
import domain.qiita.user.QiitaUserName
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleAggregateRepository extends QiitaArticleAggregateRepository {
  override def retrieveContribution()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate] = {
    sql"""
          SELECT * FROM qiita_articles AS qa
          INNER JOIN qiita_article_contributions AS qac
          ON qa.item_id = qac.item_id
          ORDER BY qac.likes_count DESC LIMIT 1000;
      """
      .map(toQiitaArticleAggregate)
      .list()
      .apply()
  }

  override def retrieveHatenaCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate] = {
    retrieve(sqls"hatena_count")
  }

  private def retrieve(orderBy: SQLSyntax)(implicit session: DBSession = AutoSession) = {
    sql"""
          SELECT * FROM qiita_articles AS qa
          INNER JOIN qiita_article_contributions AS qac
          ON qa.item_id = qac.item_id
          ORDER BY $orderBy DESC LIMIT 1000;
      """
      .map(toQiitaArticleAggregate)
      .list()
      .apply()
  }

  private def toQiitaArticleAggregate(rs: WrappedResultSet): QiitaArticleAggregate = {
    QiitaArticleAggregate(
      QiitaArticle(
        QiitaArticleId(rs.int("id")),
        QiitaItemId(rs.string("item_id")),
        QiitaArticleTitle(rs.string("title")),
        QiitaArticleUrl(rs.string("url")),
        QiitaArticlePostedDateTime(rs.zonedDateTime("posted_date_time")),
        QiitaUserName(rs.string("posted_user_name"))
      ),
      QiitaArticleContribution(
        LikesCount(rs.int("likes_count")),
        CommentsCount(rs.int("comments_count")),
        HatenaCount(rs.int("hatena_count")),
        FacebookCount(rs.int("facebook_count")),
        PocketCount(rs.int("pocket_count"))
      )
    )
  }
}
