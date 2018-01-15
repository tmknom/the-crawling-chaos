# -*- encoding:utf-8 -*-

from fabric.api import *

from env import *


def show():
    '''EC2の状態確認'''
    instance_name = get_local_env(AWS.INSTANCE_NAME)
    instance_id = get_instance_id(instance_name)
    show_instances(instance_id)


def start():
    '''EC2のスタート'''
    instance_name = get_local_env(AWS.INSTANCE_NAME)
    instance_id = get_instance_id(instance_name)
    start_instances(instance_id)


def stop():
    '''EC2のストップ'''
    instance_name = get_local_env(AWS.INSTANCE_NAME)
    instance_id = get_instance_id(instance_name)
    stop_instances(instance_id)


def show_instances(instance_id):
    command = "aws ec2 describe-instances " \
              + " --instance-ids %s " % instance_id \
              + " | jq -r '.Reservations[].Instances[] " \
              + " | {ip_address: .PublicIpAddress, status: .State.Name}' "
    local(command)


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
              + " | jq -r '.Reservations[].Instances[].InstanceId' "
    result = local(command, capture=True)
    return result
