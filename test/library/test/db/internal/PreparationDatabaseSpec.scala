package library.test.db.internal

import org.scalatest.fixture
import org.scalatestplus.play.FakeApplicationFactory
import scalikejdbc.scalatest.AutoRollback

/**
  * データベーステストに必要なトレイトを取りまとめるトレイト
  */
private[db] trait PreparationDatabaseSpec extends InitializationConnectionPool with MigrationTestDatabase with AutoRollback {
  this: fixture.TestSuite with FakeApplicationFactory =>
}
