# -*- encoding:utf-8 -*-

from fabric.api import *

from env import *
from table import TABLES


def execute():
    '''-H $SSH_HOST -u $SSH_USER_NAME --port=$SSH_PORT CSVのアップロード'''
    for table in TABLES:
        upload_csv_table(table.name)


def upload_csv_table(table_name):
    csv_path = get_local_env(PATH.CSV)
    print('%s.csv uploading...' % table_name)
    put('%s/%s.csv' % (csv_path, table_name), '/tmp/%s.csv' % table_name)
