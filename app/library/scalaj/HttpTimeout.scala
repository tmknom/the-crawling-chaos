package library.scalaj

final case class HttpTimeout(connectionTimeoutMs: Int, readTimeoutMs: Int)

object HttpTimeout {
  // デフォルト値は scalaj-http のデフォルト値の 10 倍と長めにしている
  private val DefaultConnectionTimeoutMs = 10000
  private val DefaultReadTimeoutMs       = 10000

  def default: HttpTimeout = {
    HttpTimeout(DefaultConnectionTimeoutMs, DefaultReadTimeoutMs)
  }
}
