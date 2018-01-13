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
    for table_name in TABLE_NAMES:
        download_csv_table(table_name)


def download_csv_table(table_name):
    csv_path = get_local_env('CSV_PATH')
    print('%s.csv downloading...' % table_name)
    get('/tmp/%s.csv' % table_name, '%s/%s.csv' % (csv_path, table_name))


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
