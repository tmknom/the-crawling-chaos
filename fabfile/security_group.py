# -*- encoding:utf-8 -*-

import json
from fabric.api import *

import common
from env import *


def authorize():
    '''セキュリティグループの許可'''
    revoke()
    current_ip_address = get_current_ip_address()
    name = get_security_group_name()
    authorize_security_group(name, current_ip_address)


def revoke():
    '''セキュリティグループの剥奪'''
    name = get_security_group_name()
    revoke_security_group(name)


def authorize_security_group(name, current_ip_address):
    security_group_id = get_security_group_id(name)
    ports = get_ports()
    for port in ports:
        authorize_security_group_ingress(current_ip_address, security_group_id, port)


def revoke_security_group(name):
    security_group_id = get_security_group_id(name)
    cidr_blocks = get_cidr_blocks(security_group_id)
    for cidr_block in cidr_blocks:
        if cidr_block != '127.0.0.1/32':  # localhost は残しておく
            all_revoke_security_group_ingress(cidr_block, security_group_id)


def authorize_security_group_ingress(current_ip_address, security_group_id, port):
    command = "aws ec2 authorize-security-group-ingress " \
              + " --protocol tcp " \
              + " --group-id %s " % security_group_id \
              + " --port %s " % port \
              + " --cidr %s/32 " % current_ip_address
    local(command)


def all_revoke_security_group_ingress(cidr_block, security_group_id):
    ports = get_ports()
    for port in ports:
        revoke_security_group_ingress(cidr_block, security_group_id, port)


def revoke_security_group_ingress(cidr_block, security_group_id, port):
    command = "aws ec2 revoke-security-group-ingress " \
              + " --protocol tcp " \
              + " --group-id %s " % security_group_id \
              + " --port %s " % port \
              + " --cidr %s " % cidr_block
    local(command)


def get_security_group_id(name):
    command = "aws ec2 describe-security-groups " \
              + " --filters " \
              + " 'Name=tag-key,Values=Name' " \
              + " 'Name=tag-value,Values=%s' " % name \
              + " | jq -r '.SecurityGroups[].GroupId' "
    result = local(command, capture=True)
    return result


def get_cidr_blocks(security_group_id):
    command = "aws ec2 describe-security-groups " \
              + " --group-ids %s " % (security_group_id) \
              + " | jq '.SecurityGroups[0].IpPermissions[0].IpRanges | map(.CidrIp)' "
    result = local(command, capture=True)
    return json.loads(result)


def get_security_group_name():
    return common.get_local_env(AWS.SECURITY_GROUP_NAME)


def get_ports():
    return [
        common.get_local_env(SSH.PORT),
        # 9000,
    ]


def get_current_ip_address():
    import urllib2
    try:
        return urllib2.urlopen('http://inet-ip.info/ip').read()
    except:
        import json
        return json.loads(urllib2.urlopen('http://httpbin.org/ip').read())['origin']
