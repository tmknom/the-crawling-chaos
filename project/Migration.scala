/**
  * マイグレーション
  */

import java.io.File

import com.typesafe.config.ConfigFactory
import org.flywaydb.sbt.FlywayPlugin.autoImport._

import scala.collection.JavaConverters._

object Migration {
  // application.confの読み込み
  private lazy val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()

  val Settings = Seq(
    /**
      * データベースの接続設定を application.conf から取得
      */
    flywayUrl := conf.getString("db.default.url"),
    flywayUser := conf.getString("db.default.username"),
    flywayPassword := conf.getString("db.default.password"),

    /**
      * flyway の設定を application.conf から取得
      */
    flywayLocations := conf.getStringList("flyway.locations").asScala,
    flywayOutOfOrder := conf.getBoolean("flyway.outOfOrder")
  )
}
