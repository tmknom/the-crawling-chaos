package library.scalaj

final case class HttpTimeout(connectionTimeoutMs: Int, readTimeoutMs: Int)

object HttpTimeout {
  // デフォルト値は scalaj-http のデフォルト値をそのまま踏襲している
  private val DefaultConnectionTimeoutMs = 1000
  private val DefaultReadTimeoutMs       = 1000

  def default: HttpTimeout = {
    HttpTimeout(DefaultConnectionTimeoutMs, DefaultReadTimeoutMs)
  }
}
