#!/bin/bash

SHELL_FOLDER=$(dirname "$0")

# 启动 nginx 服务
docker stack deploy -c ${SHELL_FOLDER}/cloud-nginx.yml cloud_nginx;