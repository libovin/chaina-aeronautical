#!/bin/sh

. ./env.sh

APP_NAME=chaina-aeronautical.jar

tpid=$(ps aux | grep $APP_NAME | grep -v 'grep' | awk '{print $2}')

if [ ${tpid} ]; then
  echo "${APP_NAME} is running in ${active_profile}"
else
  echo "${APP_NAME} is not running"
fi
