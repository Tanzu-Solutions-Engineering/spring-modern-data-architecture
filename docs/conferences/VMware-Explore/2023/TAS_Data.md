# Conference Play Book


Run the following to setup environment

```shell
./deployments/cloud/cloudFoundry/scripts/cf-setup.sh
```



Open Retail Web 

```shell
export WEB_APP_HOST=`cf apps | grep retail-web-app  | awk  '{print $5}'`
echo $WEB_APP_HOST
open https://$WEB_APP_HOST
```

Tail retail-analytics-app logs

```shell
cf logs retail-analytics-app
```



Get rabbit Dashboard, username/password

```shell
cf service-key retail-rabbitmq retail-rabbitmq-key | grep dashboard_url && cf service-key retail-rabbitmq retail-rabbitmq-key | grep -m1 username && cf service-key retail-rabbitmq retail-rabbitmq-key | grep -m1 password
```

Step

- Login with user/password
- Goto Exchanges -> retail.products
- REQUIRED: Add header
    - contentType=application/json


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
- Click Publish Message

This will load products into MySQL

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


```shell
curl -X 'POST' \
  https://$SOURCE_APP_HOST/retail/orders -k \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '"7","nyla","sku4","1"
"7","nyla","sku1","1"'
```


--------------

# Destroy Environment

```shell
./deployments/cloud/cloudFoundry/scripts/cf-destroy.sh
```
