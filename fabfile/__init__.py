# -*- encoding:utf-8 -*-

from fabric.api import *


@task
def deploy():
    '''デプロイ'''
    local('git checkout develop')
    local('git pull --rebase origin develop')
    local('sbt dist')
    local('rm -rf /tmp/qiita-ranker-1.0-SNAPSHOT')
    local('unzip target/universal/qiita-ranker-1.0-SNAPSHOT.zip -d /tmp')


@task
def download_csv():
    '''-H $SSH_HOST -u $SSH_USER_NAME --port=$SSH_PORT CSVのダウンロード'''
    import download_csv
    download_csv.execute()


@task
def export_data():
    '''データのエクスポート'''
    import export_data
    export_data.execute()


@task
def init_db():
    '''データベースの初期化'''
    import init_db
    init_db.execute()
