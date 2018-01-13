# -*- encoding:utf-8 -*-

from fabric.api import *

TABLE_NAMES = [
    'qiita_user_names',
    'raw_qiita_internal_user_jsons',
    'qiita_users',
    'qiita_user_contributions',
    'qiita_user_contribution_histories',
    'qiita_article_ids',
]


def get_local_env(env_name):
    return local('echo $%s' % (env_name), capture=True)
