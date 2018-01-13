# -*- encoding:utf-8 -*-

from fabric.api import *

TABLE_NAMES = [
    'qiita_user_names',
    'raw_qiita_internal_user_jsons',
    'qiita_users',
    'qiita_user_contributions',
    'qiita_user_contribution_histories'
]


@task
def export_data():
    '''データのエクスポート'''
    local('sudo rm -rf /tmp/*.csv')
    export_data_table('qiita_user_names', 'user_name ASC')
    export_data_table('raw_qiita_internal_user_jsons', 'crawled_date_time ASC')
    export_data_table('qiita_users', 'qiita_user_id ASC')
    export_data_table('qiita_user_contributions', 'updated_date_time ASC')
    export_data_table('qiita_user_contribution_histories', 'registered_date_time ASC')
    local('ls -alh /tmp/*.csv')
    local('wc -l /tmp/*.csv')


def export_data_table(table_name, order):
    execute_sql(export_data_sql(table_name, order))


def export_data_sql(table_name, order):
    return ' SELECT * FROM %s ' % table_name \
           + ' ORDER BY %s ' % order \
           + ' INTO OUTFILE \'/tmp/%s.csv\' ' % table_name \
           + ' FIELDS TERMINATED BY \'\t\' ' \
           + ' ENCLOSED BY \'\\"\' ' \
           + ' ESCAPED BY \'\\"\' ; '


@task
def init_db():
    '''データベースの初期化'''
    clean_migrate()
    load_data()


def clean_migrate():
    '''クリーンマイグレーション'''
    command = "sbt flywayClean flywayMigrate"
    local(command)


def load_data():
    '''初期データのロード'''
    for table_name in TABLE_NAMES:
        load_data_table(table_name)


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
