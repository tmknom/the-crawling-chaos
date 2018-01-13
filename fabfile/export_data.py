# -*- encoding:utf-8 -*-

from fabric.api import *

import common


def execute():
    '''データのエクスポート'''
    local('sudo rm -rf /tmp/*.csv')
    export_data_table('qiita_user_names', 'user_name ASC')
    export_data_table('raw_qiita_internal_user_jsons', 'crawled_date_time ASC')
    export_data_table('qiita_users', 'qiita_user_id ASC')
    export_data_table('qiita_user_contributions', 'updated_date_time ASC')
    export_data_table('qiita_user_contribution_histories', 'registered_date_time ASC')
    export_data_table('qiita_article_ids', 'id ASC')
    local('ls -alh /tmp/*.csv')
    local('wc -l /tmp/*.csv')


def export_data_table(table_name, order):
    common.execute_sql(export_data_sql(table_name, order))


def export_data_sql(table_name, order):
    return ' SELECT * FROM %s ' % table_name \
           + ' ORDER BY %s ' % order \
           + ' INTO OUTFILE \'/tmp/%s.csv\' ' % table_name \
           + ' FIELDS TERMINATED BY \'\t\' ' \
           + ' ENCLOSED BY \'\\"\' ' \
           + ' ESCAPED BY \'\\"\' ; '
