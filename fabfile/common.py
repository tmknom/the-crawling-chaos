# -*- encoding:utf-8 -*-

from fabric.api import *

TAR_NAME = 'csv.tar.bz2'


def execute_sql(sql):
    command = 'mysql -uroot db_production -e "%s"' % (sql)
    local(command)
