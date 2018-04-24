# 運用

## Qiitaユーザ

### Qiitaユーザ名をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-user-name-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.daily.user.QiitaUserNameCrawlerCli
```

### Qiitaユーザをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-user-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.daily.user.QiitaUserCrawlerCli
```

### トップQiitaユーザをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/top-qiita-user-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.monthly.user.TopQiitaUserCrawlerCli
```

### コントリービュートしたことのあるQiitaユーザをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/contributed-qiita-user-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.monthly.user.ContributedQiitaUserCrawlerCli
```

### Qiitaユーザを登録

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/registration-qiita-user-name-cli "xxxx, yyyy" > /dev/null 2>&1 &

run-main presentation.cli.operation.RegistrationQiitaUserNameCli "xxxx, yyyy"
```

### 無効なQiitaユーザを削除

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/deletion-unavailable-qiita-user-name-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.monthly.user.DeletionUnavailableQiitaUserNameCli
```

### 指定したQiitaユーザをクロール

```
run-main presentation.cli.operation.CrawlOneQiitaUserCli "xxxx"
```

## Qiita記事

### 記事IDをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-article-id-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.daily.article.QiitaArticleIdCrawlerCli
```

### 記事をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-article-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.daily.article.QiitaArticleCrawlerCli
```

### トップ記事をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/top-qiita-article-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.batch.monthly.article.TopQiitaArticleCrawlerCli
```

### 指定した記事IDを削除

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/deletion-qiita-item-id-cli xxxx

run-main presentation.cli.operation.DeletionQiitaItemIdCli xxxx
```
