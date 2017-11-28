[![CircleCI](https://circleci.com/gh/tmknom/play-starter.svg?style=svg)](https://circleci.com/gh/tmknom/play-starter)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4a63301e1b2e460492411c8d513c70ab)](https://www.codacy.com/app/tmknom/play-starter)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/4a63301e1b2e460492411c8d513c70ab)](https://www.codacy.com/app/tmknom/play-starter)

# qiita-ranker

## 使い方

### Qiitaユーザのイニシャルのページ数のクロール

[Qiitaのユーザ一覧](https://qiita.com/users)のクローリングの事前準備として、各イニシャルのページ数を取得する。

```
run-main presentation.cli.operation.crawler.QiitaUserInitialCrawlerCli
```

### Qiitaユーザの全件クロール

[Qiitaのユーザ一覧](https://qiita.com/users)からユーザ名を全件クロール。
[Qiita API](https://qiita.com/api/v2/docs#ユーザ)では、全件取得できないので、HTMLをクロールしている。

```
run-main presentation.cli.operation.crawler.QiitaUserCrawlerCli
```

### Qiita User Rankingのクロール

[Qiita User Ranking](https://qiita-user-ranking.herokuapp.com/)からユーザ名といいね数を全件クロール。
2017年3月頃との比較を行いたいので実装している。

```
run-main presentation.cli.operation.crawler.QiitaUserRankingCrawlerCli
```

### Qiita User Rankingにエントリーしているユーザのいいね数クロール

[Qiita User Ranking](https://qiita-user-ranking.herokuapp.com/)に出てくるユーザのいいね数をクロール。

```
run-main presentation.cli.operation.crawler.QiitaUserRankingContributionCrawlerCli
```

## チートシート

### テーブルのエクスポート

```sql
SELECT * FROM qiita_users
  INTO OUTFILE '/tmp/qiita_users.csv'
  FIELDS TERMINATED BY '\t'
  ENCLOSED BY '"'
  ESCAPED BY '"';
```

### テーブルのインポート

```sql
LOAD DATA INFILE '/tmp/qiita_users.csv'
  INTO TABLE qiita_users
  FIELDS TERMINATED BY '\t'
  ENCLOSED BY '"'
  ESCAPED BY '"';
```

## 新しいプロジェクトの始め方

### Codacy

* GitHubとのヒモ付を有効化
* CircleCIに環境変数にセットするトークンを払い出し
 * CODACY_PROJECT_TOKEN

### CircleCIに設定する

* GitHubとのヒモ付を有効化
* CircleCIに環境変数追加
 * CACHE_KEY : CircleCIのキャッシュ制御用のキー
 * CODACY_PROJECT_TOKEN : カバレッジ連携用のCodacyのトークン
 * ARTIFACT_REPOSITORY : アーティファクトリポジトリのS3
 * AWS_ACCESS_KEY_ID : AWSのアクセスキー／アーティファクトリポジトリのS3アクセス用
 * AWS_SECRET_KEY : AWSのシークレットキ／アーティファクトリポジトリのS3アクセス用

### 本番の環境変数設定

* APPLICATION_SECRET
  * sbt playGenerateSecret で生成すると良い


## テスト

```
sbt test
```

## マイグレーション

```
sbt flywayMigrate
sbt flywayClean # 全部消したい場合
```

## 起動

```
sbt run
```

## 静的解析：Scalastyle

```
sbt scalastyle
```

### コピペチェック

```
sbt cpd
```

## 依存ライブラリのバージョンアップチェック

```
sbt dependencyUpdates
```

## コードの統計情報

```
sbt stats
```

## データベースの起動

```
mysql.server start
```

## IntelliJ向けの設定

IntelliJのsbtの設定で `Use sbt shell for build and import` にチェックを入れる。

IntelliJが環境変数を認識できる `~/.bashrc` に下記を追加。

```
export ARTIFACT_REPOSITORY=xxxx
export AWS_ACCESS_KEY_ID=xxxx
export AWS_SECRET_KEY=xxxx
```
