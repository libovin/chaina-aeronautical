#!/bin/sh

. ./env.sh

APP_NAME=china-aeronautical.jar
APP_PATH=$kgms_home/lib/$APP_NAME
APP_CONF=$kgms_home/conf/application-${active_profile}.properties,$kgms_home/conf/plantdata-manage-${active_profile}.properties
echo $APP_NAME
echo $APP_PATH
echo $APP_CONF

tpid=$(ps aux | grep $APP_NAME | grep -v 'grep' | awk '{print $2}')

if [ ${tpid} ]; then
  echo "${APP_NAME} is running..."
else
  nohup java -Xms500m -Xmx500m -jar ${APP_PATH}  --spring.profiles.active=${active_profile} --spring.config.location=${APP_CONF} > /dev/null 2>&1 &
  echo Success!
fi
