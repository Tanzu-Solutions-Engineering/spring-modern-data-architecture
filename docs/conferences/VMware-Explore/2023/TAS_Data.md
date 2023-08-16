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



## retail-cache-sink-app

app going to pull messages off the recommendations queue and store them appropriately in gemfire

```shell
cf push retail-cache-sink-app -f deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml -p applications/retail-cache-sink-app/target/retail-cache-sink-app-0.0.1-SNAPSHOT.jar
```



## retail-web-app

frontend that will display recommendation information

```shell
cf push retail-web-app -f deployments/cloud/cloudFoundry/apps/retail-web-app/retail-web-app.yaml -p applications/retail-web-app/target/retail-web-app-0.0.1-SNAPSHOT.jar
```

## Review Manifest

open up that retail-analytics-app manifest
```shell
cat deployments/cloud/cloudFoundry/apps/retail-analytics-app/retail-analytics-app.yaml
```

open up retail-cache-sink-app

```shell
cat deployments/cloud/cloudFoundry/apps/retail-cache-sink-app/retail-cache-sink-app.yaml
```
retail-web-app

```shell
cat deployments/cloud/cloudFoundry/apps/retail-web-app/retail-web-app.yaml 
```
--------------------------------
# Look at MySQL

Seed Products

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

Open JDBC console in SWAGGER

```roomsql
select * from products where id in ('sku1','sku2','sku3','sku4')
```

Tail retail-analytics-app logs

```shell
cf logs retail-analytics-app
```

Load products via the source app.

- [product json](https://raw.githubusercontent.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/main/scripts/generate_customer_orders/resources/products_full.json)
- Load Products using swagger UI source app
  ```roomsql
  select count(*) from products
  ```
  
```roomsql
select * from products where id in ('sku1','sku2','sku3','sku4')
```


# Customer Orders



Open Web Application

- cf apps



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
    {"productId":"sku1","quantity":4},
    {"productId":"sku2","quantity":4}
  ]}
```


### Buy Bread


Bread, Peanut and Jelly

```csv
"7","nyla","sku3","1"
"7","nyla","sku1","1"
"7","nyla","sku2","1"
```

- Click publish message

No recommendations

### WHY NO Jelly (recommendation)

```csv
"8","nyla","sku1","1"
"8","nyla","sku3","1"
"8","nyla","sku4","1"
```


--------------

# Destroy Environment

```shell
./deployments/cloud/cloudFoundry/scripts/cf-destroy.sh
```
