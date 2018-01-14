# -*- encoding:utf-8 -*-

from fabric.api import *

from env import *
from table import TABLES


def execute():
    '''-H $SSH_HOST -u $SSH_USER_NAME --port=$SSH_PORT CSVのダウンロード'''
    for table in TABLES:
        download_csv_table(table.name)


def download_csv_table(table_name):
    csv_path = get_local_env(PATH.CSV)
    print('%s.csv downloading...' % table_name)
    get('/tmp/%s.csv' % table_name, '%s/%s.csv' % (csv_path, table_name))
