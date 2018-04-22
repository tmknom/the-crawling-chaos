# sbtコマンド

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
