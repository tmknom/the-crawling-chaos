# -*- encoding:utf-8 -*-

from fabric.api import *


def execute():
    '''デプロイ'''
    local('./deploy.sh')
