# -*- encoding:utf-8 -*-

from fabric.api import *


def execute_sql(sql):
    command = 'mysql -uroot db_production -e "%s"' % (sql)
    local(command)
