# -*- encoding:utf-8 -*-

from fabric.api import *


@task
def init_db():
    '''データベースの初期化'''
    command = "time sbt flywayClean flywayMigrate"
    local(command)
