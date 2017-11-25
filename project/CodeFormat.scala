/**
  * コードフォーマットの設定
  */

import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport.scalafmtOnCompile
import sbt._

// noinspection TypeAnnotation
object CodeFormat {
  val Settings = Seq(
    /**
      * コンパイル前に自動的に scalafmt を実行する
      *
      * @see https://github.com/lucidsoftware/neo-sbt-scalafmt#task-configuration
      */
    scalafmtOnCompile in ThisBuild := true
  )
}
