#!/bin/bash
BUILD_PATH=$(ls /home/ubuntu/app/kiwi/step2/zip/*.jar)
JAR_NAME=$(basename $BUILD_PATH)
echo "> build 파일명: $JAR_NAME"

echo "> build 파일 복사"
DEPLOY_PATH=/home/ubuntu/app/kiwi/

cp $BUILD_PATH $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl $JAR_NAME | grep jar | awk '{print $1}')
DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $DEPLOY_JAR 배포"

JAR_NAME=$(ls -tr $DEPLOY_PATH*.jar | tail -n 1)
echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar $DEPLOY_JAR , /home/ubuntu/app/kiwi/application-real-db.properties &