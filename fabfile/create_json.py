# -*- encoding:utf-8 -*-

from fabric.api import *


def create():
    '''JSONの生成'''
    cleanup_dir()
    create_ranking()


def cleanup_dir():
    local("rm -rf /var/opt/qiita-ranker")
    local("mkdir -p /var/opt/qiita-ranker/users")
    local("mkdir -p /var/opt/qiita-ranker/ranking/users")
    local("mkdir -p /var/opt/qiita-ranker/ranking/articles")


def create_ranking():
    local("sbt 'run-main presentation.cli.operation.QiitaUserRankingCli'")
