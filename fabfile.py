# -*- encoding:utf-8 -*-

from fabric.api import *


@task
def init_db():
    '''データベースの初期化'''
    clean_migrate()
    load_data()


@task
def clean_migrate():
    '''クリーンマイグレーション'''
    command = "sbt flywayClean flywayMigrate"
    local(command)


@task
def load_data():
    '''初期データのロード'''
    load_data_table('qiita_user_names')
    load_data_table('raw_qiita_internal_user_jsons')
    load_data_table('qiita_users')
    load_data_table('qiita_user_contributions')
    load_data_table('qiita_user_contribution_histories')


def load_data_table(table_name):
    execute_sql(load_data_sql(table_name))
    execute_sql(select_count_sql(table_name))
    print('\n')


def load_data_sql(table_name):
    csv_path = get_local_env('CSV_PATH')
    return ' LOAD DATA INFILE \'%s/%s.csv\' ' % (csv_path, table_name) \
           + ' INTO TABLE %s ' % table_name \
           + ' FIELDS TERMINATED BY \'\t\' ' \
           + ' ENCLOSED BY \'\\"\' ' \
           + ' ESCAPED BY \'\\"\' ; '


def select_count_sql(table_name):
    return 'select count(*) from %s' % table_name


def execute_sql(sql):
    command = 'mysql -uroot db_production -e "%s"' % (sql)
    local(command)


def get_local_env(env_name):
    return local('echo $%s' % (env_name), capture=True)
