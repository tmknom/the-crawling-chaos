/**
  * 静的解析ツールの設定
  */

import com.timushev.sbt.updates.UpdatesKeys._
import de.johoop.cpd4sbt.CopyPasteDetector.autoImport._
import org.scalastyle.sbt.ScalastylePlugin.{scalastyle, scalastyleSources}
import play.sbt.routes.RoutesKeys.routes
import sbt.Keys._
import sbt._
import wartremover.WartRemover.autoImport._

// noinspection TypeAnnotation
object StaticAnalysis {
  val Settings = WartRemover.Settings ++ Scalastyle.Settings ++ CPD.Settings ++ Updates.Settings
  val PlaySettings = WartRemover.PlaySettings

  object Scalastyle {

    val Settings = Seq(
      /**
        * Scalastyleでテストコード側もデフォルトでチェックするよう設定
        *
        * テストコードもプロダクトコード同様の品質にすべきである。
        *
        * 標準ではテストコードをチェックする場合 sbt test:scalastyle を叩くことになるが、
        * この設定を入れることで sbt scalastyle を叩いたときも、テストコードをチェックしてくれる。
        */
      scalastyleSources in Compile ++= (sourceDirectories in Test).value,

      /**
        * テスト時に Scalastyle も一緒に実行するよう設定
        *
        * Scalastyle はテストコードも含めてチェックする。
        * テスト時もコンパイル時と同様の設定でチェックしたいので Compile を指定している。
        * こうすることで compile 用の設定をそのまま流用することが可能。
        *
        * @see http://www.scalastyle.org/sbt.html
        * @see http://qiita.com/mtn81/items/ce482ed16a19f770cc68
        */
      (test in Test) := {
        (scalastyle in Compile).toTask("").value
        (test in Test).value
      }
    )
  }

  object WartRemover {

    val PlaySettings = Seq(
      /**
        * Play アプリケーションの場合は routes 関連ファイルを除外
        *
        * わざわざ、別で定義しているのは Play アプリケーション以外で下記記述をすると
        * コンパイルエラーで通らなくなってしまうため。
        *
        * 本当は右記のように書こうとした => wartremoverExcluded += baseDirectory.value / "conf" / "routes"
        * が、除外してくれなかったので、このような書き方に落ち着いた。たぶん、conf配下のファイルは扱いが特殊なんだろう。
        * http://stackoverflow.com/questions/34788530/wartremover-still-reports-warts-in-excluded-play-routes-file
        */
      wartremoverExcluded ++= routes.in(Compile).value
    )

    val Settings = Seq(
      /**
        * Wartremover のプロダクトコードとテストコードの共通設定
        *
        * - 警告の除外設定
        *   - Overloading : オーバーロードは普通に使われるものであるため除外
        *   - Throw : 例外のスローは普通に使われるものであるため除外
        * - 参考
        *   - http://www.wartremover.org/doc/install-setup.html
        */
      wartremoverWarnings in(Compile, compile in Compile) ++= Warts.allBut(Wart.Overloading, Wart.Throw),

      /**
        * WartRemover のテストコードのみの設定
        *
        * - 警告の除外設定
        *   - OptionPartial : コントローラでよく出てくるイディオム route(app, FakeRequest(GET, "/any/url")).get が引っかかるため除外
        *   - NonUnitStatements : DSL的に書かれるテストコードで、ムダに警告が出されるため除外
        *
        * - OptionPartial を抑制する詳細な理由
        *   - 抑制しない場合、コントローラのテストなどで「Option#get is disabled - use Option#fold instead」という警告が出る。
        *   - プロダクトコードでは確かに、Option 型で get メソッドを使わず、fold メソッドを使うというのは良い習慣に思える。
        *   - 一方で、コントローラのテストでは route(app, FakeRequest(GET, "/any/url")).get というイディオムがよく出てくる。
        *   - コントローラのテストでは、fold メソッドなどを使うと逆にテストの見通しが悪くなるように見える。
        *   - よって、テストコードでは OptionPartial を抑制することにした。
        *   - 本当はコントローラのテストコードだけ指定とかできればよいが、そこは妥協することとした。
        */
      wartremoverWarnings in(Test, compile in Test) --= Seq(Wart.OptionPartial, Wart.NonUnitStatements)
    )
  }

  object CPD {

    val Settings = Seq(
      /**
        * CPD によるコピペチェックの設定
        *
        * これを入れないと、全部コピペチェックに引っかかる。
        * target ディレクトリも対象になっちゃってる？
        */
      cpdSkipDuplicateFiles := true,

      /**
        * ここで設定した単語数以上が重複していたら、コピペチェックで引っかける
        *
        * デフォルトでは 100 と、やや大きめなので、少し小さめの値をセットする。
        *
        * @see https://github.com/sbt/cpd4sbt/blob/master/src/main/scala/de/johoop/cpd4sbt/Settings.scala#L33
        */
      cpdMinimumTokens := 30
    )
  }

  object Updates {

    val Settings = Seq(
      /**
        * sbt-updates の依存ライブラリアップデートチェックの対象外を設定
        *
        * dependencyUpdatesExclusions は非推奨になったから代わりに
        * dependencyUpdatesFilter を使えって書いてあるけど、なぜか使えない。。
        *
        * - net.sourceforge.pmd
        *   - cpd はライブラリが更新されてないので除外
        * - twirl-api
        *   - Scala ベースのテンプレートエンジンで、そもそも不要だが Play では標準で組み込まれてしまう
        *   - だが Play とライフサイクルが異なるらしく Play を最新にしても、アップデートを促されるケースが多い
        *   - 使いもしない twirl-api を最新にしておく必要はなく、ノイズにもなるので、チェック対象から除外する
        * - mysql-connector-java
        *   - なぜか 8.X 系にアップデートを促される
        *   - 少なくとも Play では 5.X 系を使うのが正しそうなので、チェック対象外にしてしまう
        */
      dependencyUpdatesExclusions := moduleFilter(organization = "net.sourceforge.pmd")
        | moduleFilter(name = "twirl-api")
        | moduleFilter(name = "mysql-connector-java")
    )
  }

}
