package library.test.db.internal

import library.flyway.Flyway
import org.scalatest.TestSuite
import org.scalatestplus.play.FakeApplicationFactory
import play.api.{Application, Logger}

/**
  * テストデータベースのマイグレーション
  */
private[internal] trait MigrationTestDatabase {
  // org.scalatestplus.play.BaseOneAppPerTest を参考に実装
  self: TestSuite with FakeApplicationFactory =>

  /**
    * 本トレイトを extends すると Spec テスト実行時に自動的にテストデータベースをマイグレーションさせる
    *
    * すでにマイグレーションならば何もしない
    */
  MigrationTestDatabase.migrate(fakeApplication())
}

private[internal] object MigrationTestDatabase {

  /**
    * マイグレーション済みかどうかを保持するフラグ
    *
    * [[library.datetime.DateTimeProvider]] の Clock オブジェクト同様、
    * スレッドが値を参照する際に、必ず最新の値を見るようにしておく
    *
    * @see https://www.ibm.com/developerworks/jp/java/library/j-jtp06197.html
    */
  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  @volatile
  private var migrated = false

  def migrate(app: Application): Unit = {
    if (!migrated) {
      val injector = app.injector
      Flyway(injector).clean()
      Flyway(injector).migrate()
      migrated = true
      Logger.info("Migrated test database.")
    }
  }
}
