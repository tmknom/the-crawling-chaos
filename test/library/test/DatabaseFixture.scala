package library.test

import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.bind.DefaultBinderConfiguration
import com.ninja_squad.dbsetup.operation.Operation
import scalikejdbc.DBSession

/**
  * データベースフィクスチャのセットアップ
  *
  * 通常、DbSetup を使う場合は DbSetup#launch() メソッドを呼び出すが、
  * コイツは内部的にコミット処理まで行っており AutoRollback によるロールバックが無効化される。
  *
  * そのため DbSetup#launch() メソッドから、コミット処理部分を取り除いたメソッドを独自実装し、
  * データベースフィクスチャのセットアップは本メソッドを介して行うこととする。
  *
  * @see https://qiita.com/yo1000/items/a7c0825035b4fbcfa3b2
  */
object DatabaseFixture {
  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  def define(operations: Operation*)(implicit session: DBSession): Unit = {
    val connection = session.connection
    connection.setAutoCommit(false)

    val operation = Operations.sequenceOf(operations: _*)
    operation.execute(connection, DefaultBinderConfiguration.INSTANCE)
  }
}
