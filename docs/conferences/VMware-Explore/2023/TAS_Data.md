# Conference Play Book


Run 

```shell
./deployments/cloud/cloudFoundry/scripts/cf-setup.sh
```



Open Retail Web 

```shell
open https://retail-web-app.apps.int.tas-labs.com
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

Past Content from [Product.json](https://raw.githubusercontent.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/main/scripts/generate_customer_orders/resources/products.json)

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

- REQUIRED: Add header
  - contentType=application/json
  - 
```json
  {"id":3,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku4","quantity":1}
  ]}
```


