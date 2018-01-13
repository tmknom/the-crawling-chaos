# -*- encoding:utf-8 -*-

from fabric.api import *

import common


def execute():
    '''-H $SSH_HOST -u $SSH_USER_NAME --port=$SSH_PORT CSVのダウンロード'''
    for table_name in common.TABLE_NAMES:
        download_csv_table(table_name)


def download_csv_table(table_name):
    csv_path = common.get_local_env('CSV_PATH')
    print('%s.csv downloading...' % table_name)
    get('/tmp/%s.csv' % table_name, '%s/%s.csv' % (csv_path, table_name))
