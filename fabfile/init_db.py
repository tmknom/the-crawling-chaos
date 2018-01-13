# -*- encoding:utf-8 -*-

from fabric.api import *

import common


def execute():
    clean_migrate()
    load_data()


def clean_migrate():
    '''クリーンマイグレーション'''
    command = "sbt flywayClean flywayMigrate"
    local(command)


def load_data():
    '''初期データのロード'''
    for table_name in common.TABLE_NAMES:
        load_data_table(table_name)


def load_data_table(table_name):
    common.execute_sql(load_data_sql(table_name))
    common.execute_sql(select_count_sql(table_name))
    print('\n')


def load_data_sql(table_name):
    csv_path = common.get_local_env('CSV_PATH')
    return ' LOAD DATA INFILE \'%s/%s.csv\' ' % (csv_path, table_name) \
           + ' INTO TABLE %s ' % table_name \
           + ' FIELDS TERMINATED BY \'\t\' ' \
           + ' ENCLOSED BY \'\\"\' ' \
           + ' ESCAPED BY \'\\"\' ; '


def select_count_sql(table_name):
    return 'select count(*) from %s' % table_name
