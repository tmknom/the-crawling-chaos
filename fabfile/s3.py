# -*- encoding:utf-8 -*-

from fabric.api import *

from env import *


def upload():
    '''S3へのアップロード'''
    csv_path = get_local_env(PATH.CSV)
    s3_bucket = get_local_env(AWS.S3_BUCKET)
    local("aws s3 sync %s s3://%s/qiita-ranker/" % (csv_path, s3_bucket))


def download():
    '''S3へのダウンロード'''
    csv_path = get_local_env(PATH.CSV)
    s3_bucket = get_local_env(AWS.S3_BUCKET)
    local("aws s3 sync s3://%s/qiita-ranker/ %s" % (s3_bucket, csv_path))
