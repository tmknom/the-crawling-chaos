# -*- encoding:utf-8 -*-

from fabric.api import *

import common
from table import TABLES

TAR_NAME = 'csv.tar.bz2'


def execute():
    '''データのエクスポート'''
    local('sudo rm -rf /tmp/*.csv')
    local('sudo rm -rf /tmp/%s' % TAR_NAME)
    for table in TABLES:
        export_data_table(table.name, table.order_query)
    with lcd('/tmp'):
        local('tar cvjf %s *.csv' % TAR_NAME)
    local('ls -alh /tmp/*.csv')
    local('wc -l /tmp/*.csv')
    local('ls -lah /tmp/%s' % TAR_NAME)


def export_data_table(table_name, order):
    common.execute_sql(export_data_sql(table_name, order))


def export_data_sql(table_name, order):
    return ' SELECT * FROM %s ' % table_name \
           + ' ORDER BY %s ' % order \
           + ' INTO OUTFILE \'/tmp/%s.csv\' ' % table_name \
           + ' FIELDS TERMINATED BY \'\t\' ' \
           + ' ENCLOSED BY \'\\"\' ' \
           + ' ESCAPED BY \'\\"\' ; '
