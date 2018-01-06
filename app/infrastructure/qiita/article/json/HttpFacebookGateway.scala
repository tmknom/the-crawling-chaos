package infrastructure.qiita.article.json

import javax.inject.{Inject, Singleton}

import domain.qiita.article.QiitaArticleUrl
import domain.qiita.article.contribution.{FacebookCount, FacebookGateway}
import library.scalaj.ScalajHttpAdaptor

@Singleton
final class HttpFacebookGateway @Inject()(scalajHttpAdaptor: ScalajHttpAdaptor) extends FacebookGateway {
  override def fetch(qiitaArticleUrl: QiitaArticleUrl): FacebookCount = {
    val response = scalajHttpAdaptor.get(qiitaArticleUrl.facebookApiUrl)
    FacebookParser(response).parse
  }
}
