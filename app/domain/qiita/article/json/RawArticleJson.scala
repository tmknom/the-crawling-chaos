package domain.qiita.article.json

import spray.json.{JsValue, JsonParser}

final case class RawArticleJson(value: String) {
  def toJsonMap: Map[String, JsValue] = {
    JsonParser(value).asJsObject.fields
  }
}
