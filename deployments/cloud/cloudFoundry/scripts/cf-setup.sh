#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#



# List Services

cf marketplace

## GemFire for Redis

#small
cf create-service p-cloudcache dev-plan-small  retail-gf-redis -c '{"gemfire_for_redis_enabled":"true","gemfire_for_redis_redundant_copies":2,"gemfire_for_redis_region_name": "GF_REDIS"}' -t redis

## My SQL

cf create-service p.mysql db-small retail-mysql


## RabbitMQ

cf create-service p.rabbitmq single-node retail-rabbitmq

#cf create-service p.rabbitmq single-node retail-rabbitmq  -c '{ "plugins": { "rabbitmq_stream": true, "rabbitmq_stream_management": true } }'

#cf update-service retail-rabbitmq -c '{ "plugins": { "rabbitmq_stream": true, "rabbitmq_stream_management": true } }'

# -----------------------------
# WAIT FOR SERVICE to be available

rabbit_status=`cf service retail-rabbitmq | grep status:`

while [[ "$rabbit_status" != *"create succeeded"* ]]
do
  echo "Waiting for Rabbitmq, current status:" $rabbit_status
  sleep 1
  rabbit_status=`cf service retail-rabbitmq | grep status:`
done


mysql_status=`cf service retail-mysql | grep status:`
echo "Waiting for mysql, current status:" $mysql_status
while [[ "$mysql_status" != *"create succeeded"* ]]
do
  echo "Waiting for mysql, current status:" $mysql_status
  sleep 1
  mysql_status=`cf service retail-mysql | grep status:`
done


gemfire_status=`cf service retail-gf-redis | grep status:`
echo "Waiting for gemfire, current status:" $gemfire_status
while [[ "$gemfire_status" != *"create succeeded"* ]]
do
  echo "Waiting for gemfire, current status:" $gemfire_status
  gemfire_status=`cf service retail-gf-redis | grep status:`
  sleep 1
done


#--------------------
# Push Applications
cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml -p applications/retail-cache-sink-app/target/retail-cache-sink-app-0.0.1-SNAPSHOT.jar

# retail-source-app
cf push retail-source-app -f deployments/cloud/cloudFoundry/apps/retail-source-app/retail-source-app.yaml -p applications/retail-source-app/target/retail-source-app-0.0.1-SNAPSHOT.jar


# retail-web-app
cf push retail-web-app -f deployments/cloud/cloudFoundry/apps/retail-web-app/retail-web-app.yaml -p applications/retail-web-app/target/retail-web-app-0.0.1-SNAPSHOT.jar


# retail-analytics-app
cf push retail-analytics-app -f deployments/cloud/cloudFoundry/apps/retail-analytics-app/retail-analytics-app.yaml -p applications/retail-analytics-app/target/retail-analytics-app-0.0.3-SNAPSHOT.jar


# jdbc-sql-console-app
cf push jdbc-sql-console-app -f deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/jdbc-sql-console-app.yaml -p applications/jdbc-sql-console-app/target/jdbc-sql-console-app-0.0.1-SNAPSHOT.jar

#-------------------
# Create a service key GemFire
cf create-service-key retail-gf-redis retail-gf-redis-key

# Inspect the service key:
cf service-key retail-gf-redis retail-gf-redis-key


#-------------------
# Create a service key RabbitMQ
cf create-service-key retail-rabbitmq retail-rabbitmq-key -c '{"tags":"administrator"}'

# Inspect the service key:
cf service-key retail-rabbitmq retail-rabbitmq-key


#-------------------
# Create a service key MySQL
cf create-service-key retail-mysql retail-mysql-key

# Inspect the service key:
cf service-key retail-mysql retail-mysql-key
