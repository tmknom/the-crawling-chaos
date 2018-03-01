package presentation.cli.onetime

import library.task.Task
import play.api.{Application, Logger}
import scalikejdbc._

/**
  * run-main presentation.cli.onetime.QiitaUserRankingOutputCli
  */
object QiitaUserRankingOutputCli extends App with Task {
  run()

  override def task(app: Application): Unit = {
    try {
      Logger.info(s"Started ${this.getClass.getSimpleName}.")
      Logger.info(s"${output()}")
      Logger.info(s"Completed ${this.getClass.getSimpleName}.")
    } catch {
      case e: Exception => Logger.error(s"Failed ${this.getClass.getSimpleName}.", e)
    }
  }

  private def output(): String = {
    retrieve().zipWithIndex.map {
      case (value, index) =>
        "| " + (index + 1).toString +
          " | @" + value("user_name") +
          " | " + value("daritsu") +
          " | " + formatNumber(value("likes_count")) +
          " | " + formatNumber(value("hatena_count")) +
          " | " + formatNumber(value("facebook_count")) +
          " | " + formatNumber(value("articles_count")) +
          " | **" + formatNumber(value("total_count")) + "** |"
    }.mkString("\n")
  }

  private def formatNumber(v: String): String = {
    f"${v.toInt}%,3d"
  }

  private def retrieve()(implicit session: DBSession = AutoSession): List[Map[String, String]] = {
    sql"""
         SELECT
           posted_user_name,
           SUM(likes_count) AS sum_likes_count,
           SUM(hatena_count) AS sum_hatena_count,
           SUM(facebook_count) AS sum_facebook_count,
           COUNT(qa.item_id) AS articles_count,
           (SUM(likes_count) / COUNT(qa.item_id)) AS daritsu,
           (SUM(likes_count) + SUM(hatena_count) + SUM(facebook_count)) AS sum_total_count
         FROM qiita_articles AS qa
         INNER JOIN qiita_article_contributions AS qac
         ON qa.item_id = qac.item_id
         WHERE DATE_FORMAT(posted_date_time,'%Y') = '2017'
         GROUP BY posted_user_name
         ORDER BY daritsu DESC, sum_total_count DESC, sum_likes_count DESC, sum_hatena_count DESC
         LIMIT 100;
       """
      .map(toMap)
      .list()
      .apply()
  }

  private def toMap(rs: WrappedResultSet): Map[String, String] = {
    Map(
      "user_name" -> rs.string("posted_user_name"),
      "daritsu" -> rs.string("daritsu"),
      "likes_count" -> rs.string("sum_likes_count"),
      "hatena_count" -> rs.string("sum_hatena_count"),
      "facebook_count" -> rs.string("sum_facebook_count"),
      "articles_count" -> rs.string("articles_count"),
      "total_count" -> rs.string("sum_total_count")
    )
  }
}
