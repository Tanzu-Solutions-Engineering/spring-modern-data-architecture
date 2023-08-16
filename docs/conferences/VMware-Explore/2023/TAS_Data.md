# Conference Play Book


## Pre-setup 

Cleanup everything and start from scratch
Create all 3 data service instances.

```shell
cd ~/workspace/spring-modern-data-architecture/
./deployments/cloud/cloudFoundry/scripts/cf-data-services.sh
```

Have jdbc console app pushed to another org and bound to the mysql

```shell
cd ~/workspace/spring-modern-data-architecture/
./deployments/cloud/cloudFoundry/apps/jdbc-sql-console-app/cf-push.sh
```

Load Products

```shell
export SOURCE_APP_HOST=`cf apps | grep retail-source-app  | awk  '{print $5}'`
echo $SOURCE_APP_HOST
```

```shell
curl -X 'POST' https://$SOURCE_APP_HOST/products -k \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  --data "@./scripts/generate_customer_orders/resources/products.json" 
```

Get rabbit Dashboard, username/password

```shell
cf service-key retail-rabbitmq retail-rabbitmq-key | grep dashboard_url && cf service-key retail-rabbitmq retail-rabbitmq-key | grep -m1 username && cf service-key retail-rabbitmq retail-rabbitmq-key | grep -m1 password
```

----------------------------------------
# Script

## retail-source-app

The first app we are going to push is the retail source app.


```shell
cf push retail-source-app -f deployments/cloud/cloudFoundry/apps/retail-source-app/retail-source-app.yaml -p applications/retail-source-app/target/retail-source-app-0.0.1-SNAPSHOT.jar
```
```shell
cat ./deployments/cloud/cloudFoundry/apps/retail-source-app/retail-source-app.yaml
```

## retail-analytics-app

push the retail analytics app

```shell
cf push retail-analytics-app -f deployments/cloud/cloudFoundry/apps/retail-analytics-app/retail-analytics-app.yaml -p applications/retail-analytics-app/target/retail-analytics-app-0.0.3-SNAPSHOT.jar
```

open up that app manifest 
```shell
cat deployments/cloud/cloudFoundry/apps/retail-analytics-app/retail-analytics-app.yaml
```


## retail-cache-sink-app

app going to pull messages off the recommendations queue and store them appropriately in gemfire

```shell
cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml -p applications/retail-cache-sink-app/target/retail-cache-sink-app-0.0.1-SNAPSHOT.jar
```

```shell
cat deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml
```


## retail-web-app

frontend that will display recommendation information

```shell
cf push retail-web-app -f deployments/cloud/cloudFoundry/apps/retail-web-app/retail-web-app.yaml -p applications/retail-web-app/target/retail-web-app-0.0.1-SNAPSHOT.jar
```

----------------------------------
# Demo Use Case

Tail retail-analytics-app logs

```shell
cf logs retail-analytics-app
```



Step

- Login with user/password
- Goto Exchanges -> retail.products
- REQUIRED: Add header
    - contentType=application/json


- Click Publish Message

This will load products into MySQL


Verify loaded data

```shell
export JDBC_CONSOLE_HOST=`cf apps | grep jdbc-sql-console-app  | awk  '{print $5}'`
echo $JDBC_CONSOLE_HOST
```

```shell
curl -X 'POST' -k \
  https://$JDBC_CONSOLE_HOST/query \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d 'select * from products limit 1'
```

Select count
```shell
curl -X 'POST' -k \
  https://$JDBC_CONSOLE_HOST/query \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d 'select count(*) from products'
```


# Customer Orders

In RabbitMQ Dashboard

Steps

- Goto Exchanges -> retail.customer.orders
- REQUIRED: Add header
  - contentType=application/json

Publish the following JSON an order

- Buy Milk, Peanut and butter

```json
  {"id":4,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku4","quantity":1},
    {"productId":"sku1","quantity":1},
    {"productId":"sku2","quantity":1}
  ]}
```


### Buy Bread

- Goto Exchanges -> retail.customer.orders
- REQUIRED: Add header
  - contentType=application/json
- 
```json
  {"id":2,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku3","quantity":1}
  ]}
```
- Click publish message

No recommendations

### Buy Milk

- Goto Exchanges -> retail.customer.orders
- REQUIRED: Add header
  - contentType=application/json
  - 
```json
  {"id":3,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku4","quantity":1}
  ]}
```


- Click publish message

### Orders using Source API with CSVs

```shell
curl -X 'POST' \
  https://$SOURCE_APP_HOST/retail/orders -k \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '"7","nyla","sku4","1"'
```

```shell
curl -X 'POST' \
  https://$SOURCE_APP_HOST/retail/orders -k \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '"7","nyla","sku4","1"
"7","nyla","sku22","1"'
```



--------------

# Destroy Environment

```shell
./deployments/cloud/cloudFoundry/scripts/cf-destroy.sh
```
