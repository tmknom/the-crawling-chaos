package library.test.internal.db

import org.scalatest.fixture
import org.scalatestplus.play.FakeApplicationFactory
import scalikejdbc.scalatest.AutoRollback

/**
  * データベーステストに必要なトレイトを取りまとめるトレイト
  */
private[test] trait PreparationDatabaseSpec extends InitializationConnectionPool with MigrationTestDatabase with AutoRollback {
  this: fixture.TestSuite with FakeApplicationFactory =>
}
