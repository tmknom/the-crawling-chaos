# -*- encoding:utf-8 -*-
#
# 環境変数のキー名の定義
#

from fabric.api import *


def get_local_env(env_name):
    return local('echo $%s' % (env_name), capture=True)


class SSH:
    PORT = 'SSH_PORT'


class PATH:
    CSV = 'CSV_PATH'


class AWS:
    INSTANCE_NAME = 'INSTANCE_NAME'
    SECURITY_GROUP_NAME = 'SECURITY_GROUP_NAME'
    S3_BUCKET = 'S3_BUCKET'
