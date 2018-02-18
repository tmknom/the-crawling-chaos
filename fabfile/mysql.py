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
    #cleanup(db_name)


# https://www.mk-mode.com/octopress/2014/03/23/mysql-getting-row-counts/
def show():
    '''MySQLの情報表示'''
    db_name = get_local_env(DB.NAME)
    tables = show_tables(db_name).splitlines()
    selects = map(select_table_rows, tables)
    sql = 'UNION ALL '.join(selects) + ' ORDER BY table_name ASC;'
    execute_sql(sql, db_name)


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
    zcat = switch_zcat_command()
    local('%s %s | mysql -uroot %s' % (zcat, dump_file_path(db_name), db_name))


# Macではgzcatでないと実行できない
# 一方、Amazon Linuxにはzcatしか入っていないので、どっちを使うか判定させる
def switch_zcat_command():
    with settings(warn_only=True):
        result = local("which gzcat", capture=True)
        if (len(result) > 0):
            return "gzcat"
        else:
            return "zcat"


def select_table_rows(table):
    return 'SELECT \'%s\' AS table_name, COUNT(*) AS table_rows FROM %s ' % (table, table)


def show_tables(db_name):
    sql = 'show tables;'
    result = local('mysql -sN -u root %s -e "%s"' % (db_name, sql), capture=True)
    return result


def execute_sql(sql, db_name=''):
    local('mysql -u root %s -e "%s"' % (db_name, sql))


def dump_file_path(db_name):
    return '/tmp/%s.gz' % db_name
