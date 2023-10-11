#
# /*
#  * Copyright 2023 VMware, Inc.
#  * SPDX-License-Identifier: GPL-3.0
#  */
#

# Create a service key MySQL
cf delete-service-key retail-mysql retail-mysql-key


# Create a service key RabbitMQ
cf delete-service-key retail-rabbitmq retail-rabbitmq-key

# Inspect the service key:
cf delete-service-key retail-gf-redis retail-gf-redis-key

# retail-analytics-app
cf delete retail-analytics-app

# retail-web-app
cf delete retail-web-app

# retail-source-app
cf delete retail-source-app


# retail-cache-sink-app
cf delete retail-cache-sink-app

# jdbc-sql-console-app
cf delete jdbc-sql-console-app

## RabbitMQ
cf delete-service retail-rabbitmq


## My SQL
cf delete-service retail-mysql

## GemFire for Redis
cf delete-service retail-gf-redis
