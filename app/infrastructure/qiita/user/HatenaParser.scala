package infrastructure.qiita.user

import domain.qiita.article.contribution.HatenaCount

private[user] final case class HatenaParser(headers: Map[String, IndexedSeq[String]]) {
  def parse: HatenaCount = {
    val location: String = headers("Location").head
    val gif   = location.split("/").last
    val count = gif.split("\\.").head
    HatenaCount(count.toInt)
  }
}
