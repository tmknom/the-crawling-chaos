# -*- encoding:utf-8 -*-

from fabric.api import *


def deploy():
    '''Firebaseへのデプロイ'''

    with lcd("frontend"):
        local("npm run build")

    local("firebase deploy")
