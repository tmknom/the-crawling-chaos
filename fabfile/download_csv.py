# -*- encoding:utf-8 -*-

from fabric.api import *

import common
from env import *


def execute():
    '''-H $SSH_HOST -u $SSH_USER_NAME --port=$SSH_PORT CSVのダウンロード'''
    csv_path = get_local_env(PATH.CSV)
    print('%s downloading...' % common.TAR_NAME)
    get('/tmp/%s' % common.TAR_NAME, '%s/%s' % (csv_path, common.TAR_NAME))
    with lcd(csv_path):
        local('tar xvjf %s' % common.TAR_NAME)
        local('rm %s' % common.TAR_NAME)
        local('ls -alh *.csv')
