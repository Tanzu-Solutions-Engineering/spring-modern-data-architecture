#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#




# cf update-service retail-rabbitmq -c '{ "plugins": { "rabbitmq_stream": true, "rabbitmq_stream_management": true } }'




-------------------

# Create a service key MySQL
cf delete-service-key retail-mysql retail-mysql-key


# Create a service key RabbitMQ
cf delete-service-key retail-rabbitmq retail-rabbitmq-key

# Inspect the service key:
cf delete-service-key retail-gf-redis retail-gf-redis-key

# retail-analytics-app
cf delete retail-analytics-app

cf delete pivotal-mysqlweb


# retail-analytics-app
cf delete retail-analytics-app

# retail-web-app
cf delete retail-web-app

# retail-source-app
cf delete retail-source-app


# retail-cache-sink-app
cf delete retail-cache-sink-app

cf delete hello-demo

## RabbitMQ
cf delete-service retail-rabbitmq


## My SQL
cf delete-service retail-mysql

## GemFire for Redis
cf delete-service retail-gf-redis
