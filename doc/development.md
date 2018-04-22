# ローカル開発環境

## IntelliJ向けの設定

IntelliJのsbtの設定で `Use sbt shell for build and import` にチェックを入れる。

IntelliJが環境変数を認識できる `~/.bashrc` に下記を追加し、プライベートライブラリを使用可能に。

```
export ARTIFACT_REPOSITORY=xxxx
export AWS_ACCESS_KEY_ID=xxxx
export AWS_SECRET_ACCESS_KEY=xxxx
```

## Firebase

```
npm install -g firebase-tools
firebase login
```

## direnv

```
# ssh
export SSH_PORT='xxxx'
export SSH_USER_NAME='xxxx'
export SSH_HOME='/home/xxxx'
export SSH_HOST='XX.XX.XX.XX'

# api token
export QIITA_ACCESS_TOKEN='xxxx'

# path
export CSV_PATH='/path/to/csv'
export JSON_PATH='/path/to/json'

# db
export DB_NAME='xxxx'

# aws
export INSTANCE_NAME='xxxx'
export SECURITY_GROUP_NAME='xxxx'
export S3_BUCKET='xxxx'
export S3_BUCKET='xxxx'
export PUBLIC_S3_BUCKET='xxxx'

# Google Analytics
export GA_CODE='UA-XXXXXXXX-X'
```
