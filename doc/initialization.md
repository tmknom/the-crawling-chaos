# 初期データ取得

## 全Qiitaユーザ名をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-user-name-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaUserNameCrawlerCli
```

## 全Qiitaユーザ情報をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-raw-internal-user-json-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaRawInternalUserJsonCrawlerCli
```

## 全Qiitaユーザ情報を登録

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-user-register-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaUserRegisterCli
```

## 全記事IDをクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-article-id-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaArticleIdCrawlerCli
```

## 全記事の情報をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-raw-props-article-json-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaRawPropsArticleJsonCrawlerCli
```

## 全記事の情報を登録

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-article-register-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaArticleRegisterCli
```

## 全記事の評価をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-article-contribution-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaArticleContributionCrawlerCli
```

## 全記事の詳細をクロール

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-raw-article-json-crawler-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaRawArticleJsonCrawlerCli
```

## 全記事のマークダウンを登録

```
/tmp/qiita-ranker-1.0-SNAPSHOT/bin/all-qiita-article-markdown-register-cli > /dev/null 2>&1 &

run-main presentation.cli.onetime.AllQiitaArticleMarkdownRegisterCli
```
