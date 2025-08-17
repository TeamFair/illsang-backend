#!/bin/bash
set -e

APP_DIR="/home/ssm-user/app"

if [ -d "$APP_DIR" ]; then
    echo "> 기존 애플리케이션 디렉토리($APP_DIR)를 삭제합니다."
    rm -rf "$APP_DIR"
fi