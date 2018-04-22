# サーバプロビジョニング

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

# Install fabric
sudo /usr/bin/pip install --upgrade pip
sudo /usr/bin/pip install fabric

# Setup dir
sudo mkdir /var/data
sudo chmod 777 /var/data

# Deploy
git clone https://github.com/tmknom/qiita-ranker.git
cd qiita-ranker
sbt dist
rm -rf /tmp/qiita-ranker-1.0-SNAPSHOT
unzip target/universal/qiita-ranker-1.0-SNAPSHOT.zip -d /tmp

# Init script
vi qiita-crawler # Edit user name
sudo cp qiita-crawler /etc/init.d
sudo chmod 755 qiita-crawler
sudo chkconfig --add qiita-crawler
sudo chkconfig qiita-crawler on
```
