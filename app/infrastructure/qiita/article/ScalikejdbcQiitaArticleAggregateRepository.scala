package infrastructure.qiita.article

import javax.inject.Singleton

import domain.qiita.article._
import domain.qiita.article.contribution._
import domain.qiita.user.QiitaUserName
import scalikejdbc._

@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter", "org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Nothing"))
@Singleton
final class ScalikejdbcQiitaArticleAggregateRepository extends QiitaArticleAggregateRepository {
  override def retrieveSumAll()(implicit session: DBSession = AutoSession): Seq[(QiitaArticleAggregate, Int)] = {
    sql"""
          SELECT *, likes_count + comments_count + hatena_count + facebook_count + pocket_count AS all_count
          FROM qiita_articles AS qa
          INNER JOIN qiita_article_contributions AS qac
          ON qa.item_id = qac.item_id
          ORDER BY all_count DESC LIMIT 1000;
      """
      .map(toQiitaArticleAggregateAndAllCount)
      .list()
      .apply()
  }

  override def retrieveContribution()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate] = {
    retrieve(sqls"likes_count")
  }

  override def retrieveCommentsCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate] = {
    retrieve(sqls"comments_count")
  }

  override def retrieveHatenaCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate] = {
    retrieve(sqls"hatena_count")
  }

  override def retrieveFacebookCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate] = {
    retrieve(sqls"facebook_count")
  }

  override def retrievePocketCount()(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate] = {
    retrieve(sqls"pocket_count")
  }

  override def retrieveYearContribution(year: Int)(implicit session: DBSession = AutoSession): Seq[QiitaArticleAggregate] = {
    retrieveYear(sqls"$year", sqls"likes_count")
  }

  private def retrieve(orderBy: SQLSyntax)(implicit session: DBSession = AutoSession) = {
    sql"""
          SELECT * FROM qiita_articles AS qa
          INNER JOIN qiita_article_contributions AS qac
          ON qa.item_id = qac.item_id
          ORDER BY $orderBy DESC, posted_date_time DESC LIMIT 10000;
      """
      .map(toQiitaArticleAggregate)
      .list()
      .apply()
  }

  private def retrieveYear(postedYear: SQLSyntax, orderBy: SQLSyntax)(implicit session: DBSession = AutoSession) = {
    sql"""
          SELECT * FROM qiita_articles AS qa
          INNER JOIN qiita_article_contributions AS qac
          ON qa.item_id = qac.item_id
          WHERE YEAR(posted_date_time) = $postedYear
          ORDER BY $orderBy DESC, posted_date_time DESC LIMIT 1000;
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

  private def toQiitaArticleAggregateAndAllCount(rs: WrappedResultSet): (QiitaArticleAggregate, Int) = {
    (toQiitaArticleAggregate(rs), rs.int("all_count"))
  }
}
