# -*- encoding:utf-8 -*-

from fabric.api import *


@task
def start_ec2():
    '''EC2のスタート'''
    import ec2
    ec2.start()


@task
def start_ec2():
    '''EC2のストップ'''
    import ec2
    ec2.stop()


@task
def deploy():
    '''デプロイ'''
    import deploy
    deploy.execute()


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
