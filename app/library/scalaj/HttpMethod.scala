package library.scalaj

sealed trait HttpMethod {
  val value: String
}

object HttpMethod {

  case object Get extends HttpMethod {
    val value: String = "GET"
  }

}
