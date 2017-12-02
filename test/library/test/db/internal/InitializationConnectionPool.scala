package library.test.db.internal

import org.scalatest.TestSuite
import scalikejdbc.ConnectionPool
import scalikejdbc.config.DBs

/**
  * コネクションプールの初期化
  */
private[internal] trait InitializationConnectionPool {
  self: TestSuite =>

  /**
    * 本トレイトを extends すると Spec テスト実行時に自動的にコネクションプールを初期化させる
    *
    * すでにコネクションプールが初期化されていれば何もしない
    */
  InitializationConnectionPool.initialize()
}

private[internal] object InitializationConnectionPool {
  def initialize(): Unit = {
    if (!ConnectionPool.isInitialized()) {
      DBs.setup()
    }
  }
}
