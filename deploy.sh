#!/bin/bash

# 定义容器名称和版本号
CONTAINER_NAME="cloud-backend"
VERSION="0.0.1"

# 构建镜像
docker build -t $CONTAINER_NAME:$VERSION .

# 停止并删除旧的容器实例
docker stop $CONTAINER_NAME || true
docker rm $CONTAINER_NAME || true

# 运行新的容器实例
docker run -d -p 8080:9090 --name $CONTAINER_NAME $CONTAINER_NAME:$VERSION