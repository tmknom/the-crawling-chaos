# -*- encoding:utf-8 -*-

from fabric.api import *


def user():
    '''ユーザのクロール'''
    local("/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-user-name-crawler-cli")
    local("/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-user-crawler-cli")


def article():
    '''記事のクロール'''
    local("/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-article-id-crawler-cli")
    local("/tmp/qiita-ranker-1.0-SNAPSHOT/bin/qiita-article-crawler-cli")
