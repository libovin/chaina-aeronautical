#!/bin/sh

APP_NAME=china-aeronautical.jar

tpid=$(ps aux | grep $APP_NAME | grep -v 'grep' | awk '{print $2}')
if [ ${tpid} ]; then
  sleep 5
  echo "$APP_NAME Stop Process..."
  kill -15 $tpid
fi

tpid=$(ps aux | grep $APP_NAME | grep -v 'grep' | awk '{print $2}')
if [ ${tpid} ]; then
  kill -9 $tpid
else
  echo "$APP_NAME Stop Success!"
fi