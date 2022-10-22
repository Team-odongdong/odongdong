#!/usr/bin/env bash

# set -e -o pipefail

PROJECT_ROOT="/home/ubuntu/odongdong/src/backend"

#echo -e "\
# +------------------------------------+
# |              1. build              |
# +------------------------------------+"
#
#echo -e "\
# +------- git pull... "
#cd $PROJECT_ROOT || exit 1
#git reset --hard origin/Develop
#git pull origin Develop

echo -e "\
 +------- build... "
cd $PROJECT_ROOT
chmod +x gradlew
$PROJECT_ROOT/gradle --stop
$PROJECT_ROOT/gradlew init
$PROJECT_ROOT/gradlew clean build -x test

