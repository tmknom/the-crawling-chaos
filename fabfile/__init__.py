# -*- encoding:utf-8 -*-

from fabric.api import *


@task
def sg_authorize():
    '''セキュリティグループの許可'''
    import security_group
    security_group.authorize()


@task
def sg_revoke():
    '''セキュリティグループの剥奪'''
    import security_group
    security_group.revoke()


@task
def ec2_show():
    '''EC2の状態確認'''
    import ec2
    ec2.show()


@task
def ec2_start():
    '''EC2のスタート'''
    import ec2
    ec2.start()


@task
def ec2_stop():
    '''EC2のストップ'''
    import ec2
    ec2.stop()


@task
def s3_upload():
    '''S3へのアップロード'''
    import s3
    s3.upload()


@task
def s3_download():
    '''S3へのダウンロード'''
    import s3
    s3.download()


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
def upoad_csv():
    '''-H $SSH_HOST -u $SSH_USER_NAME --port=$SSH_PORT CSVのアップロード'''
    import upload_csv
    upload_csv.execute()


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
