package library.scalaj

import scalaj.http.{Http, HttpOptions, HttpRequest, HttpResponse}

@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
trait ScalajHttpAdaptor {
  def get(url: String, httpParams: HttpParams = HttpParams.empty, headers: HttpHeaders = HttpHeaders.empty, timeout: HttpTimeout = HttpTimeout.default): String

  def getHeaders(url:        String,
                 httpParams: HttpParams = HttpParams.empty,
                 headers:    HttpHeaders = HttpHeaders.empty,
                 timeout:    HttpTimeout = HttpTimeout.default): Map[String, IndexedSeq[String]]
}

@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
final class RealScalajHttpAdaptor extends ScalajHttpAdaptor {

  override def get(url:        String,
                   httpParams: HttpParams = HttpParams.empty,
                   headers:    HttpHeaders = HttpHeaders.empty,
                   timeout:    HttpTimeout = HttpTimeout.default): String = {
    val response = request(url, httpParams, headers, timeout, HttpMethod.Get)
    response.body
  }

  override def getHeaders(url:        String,
                          httpParams: HttpParams = HttpParams.empty,
                          headers:    HttpHeaders = HttpHeaders.empty,
                          timeout:    HttpTimeout = HttpTimeout.default): Map[String, IndexedSeq[String]] = {
    val notRedirects = false
    val response     = request(url, httpParams, headers, timeout, HttpMethod.Get, notRedirects)
    response.headers
  }

  private def request(url:               String,
                      httpParams:        HttpParams,
                      headers:           HttpHeaders,
                      timeout:           HttpTimeout,
                      httpMethod:        HttpMethod,
                      isFollowRedirects: Boolean = true): HttpResponse[String] = {
    val httpRequest  = buildHttpRequest(url, httpParams, headers, timeout, httpMethod, isFollowRedirects)
    val httpResponse = requestAndLog(httpRequest)
    httpResponse
  }

  private def buildHttpRequest(url:               String,
                               httpParams:        HttpParams,
                               headers:           HttpHeaders,
                               timeout:           HttpTimeout,
                               httpMethod:        HttpMethod,
                               isFollowRedirects: Boolean): HttpRequest = {
    Http(url)
      .method(httpMethod.value)
      .params(httpParams.toMap)
      .headers(headers.toMap)
      .timeout(timeout.connectionTimeoutMs, timeout.readTimeoutMs)
      .option(HttpOptions.followRedirects(isFollowRedirects))
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
