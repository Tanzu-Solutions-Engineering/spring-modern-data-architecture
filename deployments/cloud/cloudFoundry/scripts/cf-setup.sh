#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#



# List Services

cf marketplace

## GemFire

#small
cf create-service p-cloudcache extra-small  retail-gemfire -t gemfire

##  SQL

cf create-service postgres on-demand-postgres-db retail-sql

cf create-service  p.mysql db-small retail-scdf-sql


## RabbitMQ

cf create-service p.rabbitmq on-demand-plan retail-rabbitmq

#cf create-service p.rabbitmq single-node retail-rabbitmq  -c '{ "plugins": { "rabbitmq_stream": true, "rabbitmq_stream_management": true } }'

#cf update-service retail-rabbitmq -c '{ "plugins": { "rabbitmq_stream": true, "rabbitmq_stream_management": true } }'

# Prometheus

cf push prometheus --docker-image prom/prometheus --var PORT=9090
# ----------------
# SCDF DataFlow

cf create-service p-dataflow standard scdf

 -c '{"services": ["retail-scdf-sql","retail-rabbitmq"] }'

# -----------------------------
# WAIT FOR SERVICE to be available

rabbit_status=`cf service retail-rabbitmq | grep status:`

while [[ "$rabbit_status" != *"create succeeded"* ]]
do
  echo "Waiting for Rabbitmq, current status:" $rabbit_status
  sleep 1
  rabbit_status=`cf service retail-rabbitmq | grep status:`
done


mysql_status=`cf service retail-sql | grep status:`
echo "Waiting for mysql, current status:" $mysql_status
while [[ "$mysql_status" != *"create succeeded"* ]]
do
  echo "Waiting for mysql, current status:" $mysql_status
  sleep 1
  mysql_status=`cf service retail-sql | grep status:`
done


gemfire_status=`cf service retail-gemfire | grep status:`
echo "Waiting for gemfire, current status:" $gemfire_status
while [[ "$gemfire_status" != *"create succeeded"* ]]
do
  echo "Waiting for gemfire, current status:" $gemfire_status
  gemfire_status=`cf service retail-gemfire | grep status:`
  sleep 1
done


mysql_status=`cf service retail-scdf-sql | grep status:`
echo "Waiting for retail-scdf-sql, current status:" $mysql_status
while [[ "$mysql_status" != *"create succeeded"* ]]
do
  echo "Waiting for retail-scdf-sql, current status:" $mysql_status
  mysql_status=`cf service retail-scdf-sql | grep status:`
  sleep 1
done


data_flow_status=`cf service data-flow | grep status:`
echo "Waiting for data-flow, current status:" $data-flow_status
while [[ "$data-flow_status" != *"create succeeded"* ]]
do
  echo "Waiting for retail-scdf-sql, current status:" $data-flow_status
  data_flow_status=`cf service data-flow | grep status:`
  sleep 1
done



#--------------------
# Push Applications
cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml -p applications/retail-cache-sink-app/target/retail-cache-sink-app-0.1.2-SNAPSHOT.jar

# retail-source-app
./deployments/cloud/cloudFoundry/apps/retail-source-app/cf-push.sh


# retail-web-app
cf push retail-web-app -f deployments/cloud/cloudFoundry/apps/retail-web-app/retail-web-app.yaml -p applications/retail-web-app/target/retail-web-app-0.1.1-SNAPSHOT.jar


# retail-analytics-app
./deployments/cloud/cloudFoundry/apps/retail-analytics-app/cf-push.sh


# jdbc-sql-console-app
cf push jdbc-sql-console-app -f deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/jdbc-sql-console-app.yaml -p applications/jdbc-sql-console-app/target/jdbc-sql-console-app-0.0.2-SNAPSHOT.jar

#-------------------
# Create a service key GemFire
cf create-service-key retail-gemfire retail-gemfire-key

# Inspect the service key:
cf service-key retail-gemfire retail-gemfire-key


#-------------------
# Create a service key RabbitMQ
cf create-service-key retail-rabbitmq retail-rabbitmq-key -c '{"tags":"administrator"}'

# Inspect the service key:
cf service-key retail-rabbitmq retail-rabbitmq-key


#-------------------
# Create a service key SQL
cf create-service-key retail-sql retail-sql-key

# Inspect the service key:
cf service-key retail-sql retail-sql-key


./deployments/cloud/cloudFoundry/apps/gemfire-gideon-console/cf-push.sh



