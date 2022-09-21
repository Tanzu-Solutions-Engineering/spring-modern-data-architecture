#!/bin/bash
# This script install GemFire and GemFire for Redis apps addon

mkdir -p ~/spring-modern-data-architecture/data-services/gemfire
curl -o ~/spring-modern-data-architecture/data-services/gemfire/vmware-gemfire-9.15.0.tgz https://spring-modern-data-architecture-files.s3.us-west-1.amazonaws.com/vmware-gemfire-9.15.0.tgz
mkdir -p ~/spring-modern-data-architecture/data-services/gemfire/
project_dir=$PWD
cd ~/spring-modern-data-architecture/data-services/gemfire/
tar -xvf ~/spring-modern-data-architecture/data-services/gemfire/vmware-gemfire-9.15.0.tgz


curl -o ~/spring-modern-data-architecture/data-services/gemfire/gemfire-for-redis-apps-1.0.1.tgz  https://spring-modern-data-architecture-files.s3.us-west-1.amazonaws.com/gemfire-for-redis-apps-1.0.1.tgz
cd ~/spring-modern-data-architecture/data-services/gemfire/
tar -xvf  ~/spring-modern-data-architecture/data-services/gemfire/gemfire-for-redis-apps-1.0.1.tgz
cd $project_dir

echo ************************************
echo GemFire installed here ~/spring-modern-data-architecture/data-services/gemfire/vmware-gemfire-9.15.0
echo GemFire for Redis Apps addone installed here  ~/spring-modern-data-architecture/data-services/gemfire/gemfire-for-redis-apps-1.0.1
