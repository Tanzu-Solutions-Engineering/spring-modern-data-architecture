Performance Testing Streams

```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/TanzuRabbitMQ/dev/tanzu-rabbitmq-event-streaming-showcase
java -jar applications/performance/lib/stream-perf-test-0.5.0.jar
```


```shell
kubectl config set-context --current --namespace=retail-explore-demo
```

Login Into Database

```shell
k exec postgres-db-0 -it -- psql postgres-db pgappuser  -n retail-explore-demo
```


Review Records

```roomsql
select count(*) from retail.products;
```

```roomsql
select * from retail.products;
```



```roomsql
select * from retail.customer_orders;
```
```roomsql
select count(*) from retail.customer_orders;
```

```shell
  curl -X 'POST' \
  'http://analytics-cloud/amqp/{exchange}/{routingKey}?exchange=retail.customer.orders&routingKey=nyla' \
  -H 'accept: */*' \
  -H 'rabbitContentType: application/json' \
  -H 'Content-Type: application/json' \
  -d '{"id":3,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku1","quantity":1},
    {"productId":"sku3","quantity":1},
    {"productId":"sku4","quantity":1}
  ]}'
```

```shell
rabbitmq-streams add_replica   retail.products.retail-analytics-app "rabbit@rabbitmq-server-1.rabbitmq-nodes.retail-explore-demo"
rabbitmq-streams add_replica   retail.products.retail-analytics-app "rabbit@rabbitmq-server-2.rabbitmq-nodes.retail-explore-demo"
```

```roomsql
select count(*) from retail.customer_orders;
```

