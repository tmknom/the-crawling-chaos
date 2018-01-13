[![CircleCI](https://circleci.com/gh/tmknom/qiita-ranker.svg?style=svg)](https://circleci.com/gh/tmknom/qiita-ranker)

# qiita-ranker

## 使い方

### Qiitaユーザ

#### Qiitaユーザ名をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-user-name-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.daily.user.QiitaUserNameCrawlerCli
```

#### Qiitaユーザをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-user-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.daily.user.QiitaUserCrawlerCli
```

#### トップQiitaユーザをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/top-qiita-user-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.monthly.user.TopQiitaUserCrawlerCli
```

#### コントリービュートしたことのあるQiitaユーザをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/contributed-qiita-user-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.monthly.user.ContributedQiitaUserCrawlerCli
```

#### 無効なQiitaユーザを削除

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/deletion-unavailable-qiita-user-name-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.monthly.user.DeletionUnavailableQiitaUserNameCli
```

### Qiita記事

#### 最近登録された記事IDをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/recently-qiita-article-id-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.daily.article.RecentlyQiitaArticleIdCrawlerCli
```

#### 最近登録された記事をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/recently-qiita-article-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.daily.article.RecentlyQiitaArticleCrawlerCli
```

### 初期データ取得

#### 全Qiitaユーザ名をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-user-name-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaUserNameCrawlerCli
```

#### 全Qiitaユーザ情報をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-raw-internal-user-json-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaRawInternalUserJsonCrawlerCli
```

#### 全Qiitaユーザ情報を登録

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-user-register-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaUserRegisterCli
```

## デプロイ

```
./deploy.sh
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

## 本番環境

### direnv

```
export SSH_PORT='xxxx'
export SSH_USER_NAME='xxxx'
export SSH_HOME='/home/xxxx'
export SSH_HOST='XX.XX.XX.XX'
```

### ログイン

```
ssh -l $SSH_USER_NAME -p $SSH_PORT $SSH_HOST
```

### サーバプロビジョニング

IntelliJ向けの設定に記述した環境変数を `~/.bash_profile` に追記。
手動で適当に作った ec2-sbt-sample ユーザのアクセスキーを払い出してセット。

```
# Install MySQL
sudo yum update -y
sudo yum install -y http://dev.mysql.com/get/mysql57-community-release-el6-7.noarch.rpm
sudo yum install -y mysql-community-server
sudo sh -c 'echo "skip-grant-tables" >> /etc/my.cnf'
sudo sh -c 'echo secure-file-priv = "" >> /etc/my.cnf'
sudo service mysqld start
mysql -u root -h 127.0.0.1 -e 'create database db_production character set utf8mb4 collate utf8mb4_bin;'

# Install sbt
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 8u152-zulu
sdk install sbt

# Deploy
git clone https://github.com/tmknom/qiita-ranker.git
cd qiita-ranker
sbt dist
rm -rf /tmp/qiita-ranker-1.0-SNAPSHOT
unzip target/universal/qiita-ranker-1.0-SNAPSHOT.zip -d /tmp
```

### データアップロード

ローカルで実行。

```
scp -P $SSH_PORT ~/qiita-ranker/*.csv $SSH_USER_NAME@$SSH_HOST:/tmp
```

### データインポート

`mysql -uroot db_production` でMySQLにログイン。

```sql
LOAD DATA INFILE '/tmp/qiita_user_initials.csv'
INTO TABLE qiita_user_initials
FIELDS TERMINATED BY '\t'
ENCLOSED BY '"'
ESCAPED BY '"';

LOAD DATA INFILE '/tmp/qiita_users.csv'
INTO TABLE qiita_users
FIELDS TERMINATED BY '\t'
ENCLOSED BY '"'
ESCAPED BY '"';

LOAD DATA INFILE '/tmp/qiita_user_rankings.csv'
INTO TABLE qiita_user_rankings
FIELDS TERMINATED BY '\t'
ENCLOSED BY '"'
ESCAPED BY '"';
```

### 実行

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-user-contribution-crawler-cli > /dev/null 2>&1 &
```

### ログ確認

```
less /tmp/qiita-ranker-1.0-SNAPSHOT/logs/application.log
```

### データのエクスポート

```sql
SELECT * FROM qiita_user_contributions
INTO OUTFILE '/tmp/qiita_user_contributions.csv'
FIELDS TERMINATED BY '\t'
ENCLOSED BY '"'
ESCAPED BY '"';
```

### データのダウンロード

```
scp -P $SSH_PORT $SSH_USER_NAME@$SSH_HOST:/home/$SSH_USER_NAME/*.csv ~/qiita-ranker
```

## Codacyの設定方法

* GitHubとのヒモ付を有効化
* CircleCIに環境変数にセットするトークンを払い出し
 * CODACY_PROJECT_TOKEN
* sbtでテスト実行時にcodacyCoverageも実行させるよう設定
