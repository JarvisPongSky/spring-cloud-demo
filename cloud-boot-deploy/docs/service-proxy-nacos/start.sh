#!/bin/bash

SHELL_FOLDER=$(dirname "$0")

# 启动 proxy_nacos 服务
docker stack deploy -c ${SHELL_FOLDER}/docker-compose.yml proxy_nacos;