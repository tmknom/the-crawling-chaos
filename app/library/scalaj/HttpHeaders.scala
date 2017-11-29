package library.scalaj

final case class HttpHeaders(private val value: Map[String, String]) {
  def toMap: Map[String, String] = value
}

object HttpHeaders {
  def empty: HttpHeaders = {
    HttpHeaders(Map.empty[String, String])
  }
}
