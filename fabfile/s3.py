# -*- encoding:utf-8 -*-

from fabric.api import *

from env import *


def upload():
    '''S3へのアップロード'''
    csv_path = get_local_env(PATH.CSV)
    s3_bucket = get_local_env(AWS.S3_BUCKET)
    local("aws s3 sync %s s3://%s/qiita-ranker/" % (csv_path, s3_bucket))


def deploy_json():
    '''S3へJSONをデプロイ'''
    upload_json('ranking')
    upload_json('users')


def upload_json(dir_name):
    json_path = get_local_env(PATH.JSON)
    s3_bucket = get_local_env(AWS.PUBLIC_S3_BUCKET)
    local('find %s/%s | grep "\.\(json\)$" | xargs gzip' % (json_path, dir_name))
    command = 'aws s3 sync %s/%s s3://%s/qiita-ranker/%s ' % (json_path, dir_name, s3_bucket, dir_name) + \
              ' --content-encoding "gzip" ' + \
              ' --content-type "application/json; charset=utf-8" '
    local(command)
