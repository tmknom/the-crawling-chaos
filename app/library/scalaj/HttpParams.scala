package library.scalaj

final case class HttpParams(private val value: Map[String, String]) {
  def toMap: Map[String, String] = value
}

object HttpParams {
  def empty: HttpParams = {
    HttpParams(Map.empty[String, String])
  }
}
