# -*- encoding:utf-8 -*-

from fabric.api import *

import common
from env import *


def start():
    '''EC2のスタート'''
    instance_name = common.get_local_env(AWS.INSTANCE_NAME)
    instance_id = get_instance_id(instance_name)
    start_instances(instance_id)


def stop():
    '''EC2のスタート'''
    instance_name = common.get_local_env(AWS.INSTANCE_NAME)
    instance_id = get_instance_id(instance_name)
    stop_instances(instance_id)


def start_instances(instance_id):
    command = "aws ec2 start-instances " \
              + " --instance-ids %s " % instance_id
    local(command)


def stop_instances(instance_id):
    command = "aws ec2 stop-instances " \
              + " --instance-ids %s " % instance_id
    local(command)


def get_instance_id(instance_name):
    command = "aws ec2 describe-instances " \
              + " --filters " \
              + " 'Name=tag-key,Values=Name' " \
              + " 'Name=tag-value,Values=%s' " % instance_name \
              + " 'Name=instance-state-name,Values=running,stopped' " \
              + " | jq -r '.Reservations[].Instances[].InstanceId' "
    result = local(command, capture=True)
    return result
