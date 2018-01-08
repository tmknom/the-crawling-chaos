set -ex

git checkout develop
git pull --rebase origin develop

sbt dist
rm -rf /tmp/qiita-ranker-1.0-SNAPSHOT
unzip target/universal/qiita-ranker-1.0-SNAPSHOT.zip -d /tmp

