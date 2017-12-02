/**
  * 依存ライブラリの定義
  *
  * sbt では Ivy を使ってライブラリ依存性の制御を実装している。
  * また sbt は、デフォルトで Maven の標準リポジトリからライブラリを取得しようとする。
  * もし Ivy が依存ライブラリそ見つけられない場合 resolver 設定を追加すること。
  *
  * @see http://www.scala-sbt.org/0.13/docs/ja/Library-Dependencies.html
  */

import play.sbt.PlayImport.{guice, jdbc}
import sbt._

object Version {
  // DB関連
  val MysqlConnectorJava = "5.1.44"
  val Scalikejdbc = "3.1.0"
  val ScalikejdbcPlayInitializer = "2.6.0"

  // JSON関連
  val SprayJson = "1.3.4"

  // 通信関連
  val ScalajHttp = "2.3.0"
  val DispatchCore = "0.13.2"
  val ScalaScraper = "2.0.0"

  // ロギング関連
  val LogstashLogbackEncoder = "4.11"
  val Janino = "3.0.7"

  // テスト関連
  val MockitoCore = "2.12.0"
  val DbSetup = "2.1.0"
  val ScalatestplusPlay = "3.1.2"

  // 共通ライブラリ関連
  val PlayFramework = "2.6.5" // plugins.sbt の定義と合わせること
  val FlywayCore = "4.2.0"
}

// noinspection TypeAnnotation
// IntelliJの警告が鬱陶しいので抑制
object Library {
  // MySQL用のJDBCドライバ
  // なお、少し情報が古いがトラップもあるようなので、本番稼働前にある程度確認したほうがよさそう
  // http://saiya-moebius.hatenablog.com/entry/2014/08/20/230445
  val MysqlConnectorJava = "mysql" % "mysql-connector-java" % Version.MysqlConnectorJava

  // SQLのクエリを組み立てるためのライブラリ
  // http://scalikejdbc.org/
  //
  // 余談だが、ScalikeJDBC 自体は日本人が開発しているようで、日本語ドキュメントが開発者によって公開されている
  // https://github.com/scalikejdbc/scalikejdbc-cookbook/tree/master/ja
  val Scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % Version.Scalikejdbc

  // Playのapplication.confでScalikeのconfigを書くためのライブラリ
  val ScalikejdbcConfig = "org.scalikejdbc" %% "scalikejdbc-config" % Version.Scalikejdbc

  // コネクションプールの作成に必要
  //
  // アプリケーション起動時に、コネクションプールの初期化するには application.conf に下記記述が必要
  // play.modules.enabled += "scalikejdbc.PlayModule"
  val ScalikejdbcPlayInitializer = "org.scalikejdbc" %% "scalikejdbc-play-initializer" % Version.ScalikejdbcPlayInitializer

  // Scala オブジェクトと JSON の相互変換ライブラリ
  // http://arata.hatenadiary.com/entry/2015/02/11/015916
  val SprayJson = "io.spray" %% "spray-json" % Version.SprayJson

  // HTTP 通信用ライブラリ
  //
  // dispatch を最初採用していたが、明示的なクローズ処理を書かないと
  // JVM のプロセスが刺さるという減少に出くわしイマイチなので scalaj-http に切り替えた
  //
  // また ws は使わないほうが良いらしい
  // http://qiita.com/bigwheel/items/44cb874ced4be204c09c
  val ScalajHttp = "org.scalaj" %% "scalaj-http" % Version.ScalajHttp

  // HTTP通信用ライブラリ
  // wsよりコッチを使うほうが推奨されているっぽい
  // http://qiita.com/bigwheel/items/44cb874ced4be204c09c
  val DispatchCore = "net.databinder.dispatch" %% "dispatch-core" % Version.DispatchCore

  // HTMLパーサ
  // https://github.com/ruippeixotog/scala-scraper
  val ScalaScraper = "net.ruippeixotog" %% "scala-scraper" % Version.ScalaScraper

  // logbackでログをjson形式で出力
  // https://github.com/logstash/logstash-logback-encoder
  val LogstashLogbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % Version.LogstashLogbackEncoder

  // logbackで分岐処理を定義するために必要
  // https://logback.qos.ch/setup.html#janino
  val Janino = "org.codehaus.janino" % "janino" % Version.Janino

  // モック用ライブラリ
  // https://www.playframework.com/documentation/2.6.x/ScalaTestingWithScalaTest#Mockito
  val MockitoCore = "org.mockito" % "mockito-core" % Version.MockitoCore % Test

  // Scalikejdbcのテストを書くためのライブラリ
  // https://github.com/scalikejdbc/scalikejdbc-cookbook/blob/master/ja/08_unittest.md
  val ScalikejdbcTest = "org.scalikejdbc" %% "scalikejdbc-test" % Version.Scalikejdbc

  // データベース用フィクスチャ定義ライブラリ　
  // http://dbsetup.ninja-squad.com/
  val DbSetup = "com.ninja-squad" % "DbSetup" % Version.DbSetup % Test

  // xUnit用ライブラリ
  // 共通ライブラリに定義するテスト用の基底クラス・ヘルパー向けに、main側からでも読めるようにする
  val ScalatestplusPlayForMain = "org.scalatestplus.play" %% "scalatestplus-play" % Version.ScalatestplusPlay

  // xUnit用ライブラリ
  // playの標準テストライブラリなので、そのまま採用する
  // https://www.playframework.com/documentation/2.6.x/ScalaTestingWithScalaTest
  val ScalatestplusPlay = ScalatestplusPlayForMain % Test

  // Play 本体（共通ライブラリ用に定義）
  // Play アプリケーション単体であれば project/plugins.sbt で読み込むので、依存関係の定義は不要
  // なお、変数名が Play ではなく PlayFramework なのは Play という名前が定義済みのため、被るのを避けるため
  val PlayFramework = "com.typesafe.play" %% "play" % Version.PlayFramework

  // Play から jdbc を使うためのラッパーインタフェース（共通ライブラリ用に定義）
  // flyway 単体で使う場合は project/plugins.sbt で読み込むので、依存関係の定義は不要
  val PlayJdbcApi = "com.typesafe.play" %% "play-jdbc-api" % Version.PlayFramework

  // マイグレーションツールf（共通ライブラリ用に定義）
  // Play アプリケーション単体であれば project/plugins.sbt で読み込むので、依存関係の定義は不要
  val FlywayCore = "org.flywaydb" % "flyway-core" % Version.FlywayCore
}

// noinspection TypeAnnotation
object Dependencies {

  import Library._

  // 最低限必要なライブラリ
  val Base = Seq(
    guice,
    jdbc,
    MysqlConnectorJava,
    Scalikejdbc,
    ScalikejdbcConfig,
    ScalikejdbcPlayInitializer,
    SprayJson,
    ScalajHttp,
    DispatchCore,
    ScalaScraper,
    LogstashLogbackEncoder,
    Janino
  )

  // テスト関連
  val Test = Seq(
    MockitoCore,
    ScalikejdbcTest,
    DbSetup,
    ScalatestplusPlay
  )

  // アプリケーションの依存関係
  val Application = Base ++ Test ++ PrivateLibrary.Dependencies
}
