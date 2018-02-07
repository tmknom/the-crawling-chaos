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


def deploy_json():
    '''S3へJSONをデプロイ'''
    json_path = get_local_env(PATH.JSON)
    s3_bucket = get_local_env(AWS.PUBLIC_S3_BUCKET)
    local('find %s | grep "\.\(json\)$" | xargs gzip' % json_path)

    command = 'aws s3 sync %s s3://%s/qiita-ranker/ ' % (json_path, s3_bucket) + \
              ' --content-encoding "gzip" ' + \
              ' --content-type "application/json; charset=utf-8" '
    local(command)
