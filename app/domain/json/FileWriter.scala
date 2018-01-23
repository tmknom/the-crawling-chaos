package domain.json

import java.io.PrintWriter

import play.api.Logger
import spray.json.JsValue

object FileWriter {
  def write(fileName: String, jsValue: JsValue): Unit = {
    val file = new PrintWriter(fileName)
    try {
      file.write(jsValue.compactPrint)
    } finally {
      file.close()
    }
    Logger.info(s"Wrote $fileName")
  }
}
