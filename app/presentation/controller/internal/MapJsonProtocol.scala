package presentation.controller.internal

import spray.json._

// scalastyle:off
// https://tech.mendix.com/scala/runtime/spray-json/2014/09/28/scala-nested-maps-to-json/
object MapJsonProtocol extends spray.json.DefaultJsonProtocol {

  implicit object MapJsonFormat extends JsonFormat[Map[String, Any]] { // 1
    def write(m: Map[String, Any]) = {
      JsObject(m.mapValues { // 2
        case v: String => JsString(v) // 3
        case v: Int    => JsNumber(v)
        //case v: Map[String, Any] => write(v) // 4
        case v: Map[_, _] => write(v.asInstanceOf[Map[String, Any]]) // 4
        case v: Any       => JsString(v.toString) // 5
      })
    }

    def read(value: JsValue) = ??? // 6
  }

}
