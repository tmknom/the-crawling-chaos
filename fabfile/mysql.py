# -*- encoding:utf-8 -*-

from fabric.api import *

from env import *


def dump():
    '''MySQLのダンプ'''
    db_name = get_local_env(DB.NAME)
    s3_bucket = get_local_env(AWS.S3_BUCKET)
    dump_mysql(db_name)
    upload_s3(db_name, s3_bucket)
    cleanup(db_name)


def restore():
    '''MySQLのリストア'''
    db_name = get_local_env(DB.NAME)
    s3_bucket = get_local_env(AWS.S3_BUCKET)
    download_s3(db_name, s3_bucket)
    drop_database(db_name)
    create_database(db_name)
    restore_mysql(db_name)
    cleanup(db_name)


def dump_mysql(db_name):
    # --opt ：デフォルト有効だが明示的にしている。
    # --skip-lock-tables　：--optが--lock-tablesを有効にするので、それを打ち消す為に使用する。
    # --single-transaction　：ダンプを行う前にBEGINステートメントを発行する。
    # --quote-names ：DB名、テーブル名、カラム名などの識別子をバックティック文字で囲む。
    #
    # https://qiita.com/PlanetMeron/items/3a41e14607a65bc9b60c
    # https://qiita.com/ryounagaoka/items/7be0479a36c97618907f
    # https://gist.github.com/koudaiii/2780b25e3cc4c12f561a
    command = "mysqldump -uroot --opt --skip-lock-tables --single-transaction --quote-names %s " % db_name \
              + " | gzip > %s " % dump_file_path(db_name)
    local(command)


def upload_s3(db_name, s3_bucket):
    command = 'aws s3 cp %s s3://%s/qiita-ranker/%s.gz' % (dump_file_path(db_name), s3_bucket, db_name)
    local(command)


def cleanup(db_name):
    local('rm %s' % dump_file_path(db_name))


def download_s3(db_name, s3_bucket):
    command = 'aws s3 cp s3://%s/qiita-ranker/%s.gz %s' % (s3_bucket, db_name, dump_file_path(db_name))
    local(command)


def drop_database(db_name):
    sql = 'DROP DATABASE IF EXISTS %s ;' % db_name
    execute_sql(sql)


def create_database(db_name):
    sql = 'CREATE DATABASE IF NOT EXISTS %s CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;' % db_name
    execute_sql(sql)


def restore_mysql(db_name):
    local('gzcat %s | mysql -uroot %s' % (dump_file_path(db_name), db_name))


def execute_sql(sql):
    local('mysql -u root -e "%s"' % sql)


def dump_file_path(db_name):
    return '/tmp/%s.gz' % db_name
