package library.scalaj

import play.api.Logger

import scalaj.http.{Http, HttpRequest, HttpResponse}

@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
object ScalajHttpAdaptor {

  def get(url:        String,
          httpParams: HttpParams = HttpParams.empty,
          headers:    HttpHeaders = HttpHeaders.empty,
          timeout:    HttpTimeout = HttpTimeout.default): String = {
    ScalajHttpAdaptor(url, httpParams, headers, timeout, HttpMethod.Get).request()
  }
}

final case class ScalajHttpAdaptor(url: String, httpParams: HttpParams, headers: HttpHeaders, timeout: HttpTimeout, httpMethod: HttpMethod) {
  private def request(): String = {
    val httpRequest  = buildHttpRequest()
    val httpResponse = requestAndLog(httpRequest)
    httpResponse.body
  }

  private def buildHttpRequest(): HttpRequest = {
    Http(url)
      .method(httpMethod.value)
      .params(httpParams.toMap)
      .headers(headers.toMap)
      .timeout(timeout.connectionTimeoutMs, timeout.readTimeoutMs)
  }

  private def requestAndLog(httpRequest: HttpRequest): HttpResponse[String] = {
    // 通信前にロギング
    Logger.debug(s"started http request ($url, $httpMethod, $httpParams, $headers, $timeout).")
    val httpResponse: HttpResponse[String] = httpRequest.asString

    // HTTPステータスコードが400系500系の場合は例外をスロー
    checkResponse(httpResponse)

    // 通信後にロギング
    Logger.debug(s"completed http request ($url, $httpMethod, $httpParams, $headers, $timeout), response (${httpResponse.body}).")
    httpResponse
  }

  private def checkResponse(httpResponse: HttpResponse[String]): Unit = {
    if (httpResponse.isError) {
      val requestMessage  = s"request ($url, $httpMethod, $httpParams, $headers, $timeout)"
      val responseMessage = s"response (${httpResponse.code}, ${httpResponse.headers.mkString(",")}, ${httpResponse.body})"
      val message         = s"failed http $requestMessage, $responseMessage."
      throw new ScalajHttpException(message)
    }
  }
}
