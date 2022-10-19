#!/usr/bin/env bash

echo -e "\
 +------------------------------------+
 |              3. start              |
 +------------------------------------+"

PROJECT_ROOT="/home/ubuntu/release-odongdong/src/backend"
JAR_FILE="$PROJECT_ROOT/build/libs/odondong-0.0.1-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" | tee -a $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." | tee -a $DEPLOY_LOG