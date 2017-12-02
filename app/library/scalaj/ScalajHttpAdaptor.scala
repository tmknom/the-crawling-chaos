package library.scalaj

import scalaj.http.{Http, HttpRequest, HttpResponse}

@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
trait ScalajHttpAdaptor {
  def get(url: String, httpParams: HttpParams = HttpParams.empty, headers: HttpHeaders = HttpHeaders.empty, timeout: HttpTimeout = HttpTimeout.default): String
}

@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
final class RealScalajHttpAdaptor extends ScalajHttpAdaptor {

  def get(url:        String,
          httpParams: HttpParams = HttpParams.empty,
          headers:    HttpHeaders = HttpHeaders.empty,
          timeout:    HttpTimeout = HttpTimeout.default): String = {
    request(url, httpParams, headers, timeout, HttpMethod.Get)
  }

  private def request(url: String, httpParams: HttpParams, headers: HttpHeaders, timeout: HttpTimeout, httpMethod: HttpMethod): String = {
    val httpRequest  = buildHttpRequest(url, httpParams, headers, timeout, httpMethod)
    val httpResponse = requestAndLog(httpRequest)
    httpResponse.body
  }

  private def buildHttpRequest(url: String, httpParams: HttpParams, headers: HttpHeaders, timeout: HttpTimeout, httpMethod: HttpMethod): HttpRequest = {
    Http(url)
      .method(httpMethod.value)
      .params(httpParams.toMap)
      .headers(headers.toMap)
      .timeout(timeout.connectionTimeoutMs, timeout.readTimeoutMs)
  }

  private def requestAndLog(httpRequest: HttpRequest): HttpResponse[String] = {
    // 通信前にロギング
    //Logger.debug(s"started http request ($url, $httpMethod, $httpParams, $headers, $timeout).")
    val httpResponse: HttpResponse[String] = httpRequest.asString

    // HTTPステータスコードが400系500系の場合は例外をスロー
    checkResponse(httpResponse)

    // 通信後にロギング
    //Logger.debug(s"completed http request ($url, $httpMethod, $httpParams, $headers, $timeout), response (${httpResponse.body}).")
    httpResponse
  }

  private def checkResponse(httpResponse: HttpResponse[String]): Unit = {
    if (httpResponse.isError) {
      val responseMessage = s"response (${httpResponse.code}, ${httpResponse.headers.mkString(",")}, ${httpResponse.body})"
      val message         = s"failed http $responseMessage."
      throw new ScalajHttpException(message)
    }
  }
}
