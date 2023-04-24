# Start Redis


```shell
java  -jar applications/spring-gf-redis-server/target/spring-gf-redis-server-0.0.3-SNAPSHOT.jar --server.port=0 --spring.data.gemfire.cache.server.port=10001 --spring.data.gemfire.name=server1 --gemfire-for-redis-port=6379
```

# Start Postgres

```shell
docker run -p 5432:5432 -d  --expose 5432 --name postgresql --rm -e POSTGRESQL_PASSWORD=password123  -h postgresql bitnami/postgresql:15.2.0
```


Access pql password123

```shell
docker run -it --rm \
    bitnami/postgresql:15.2.0 psql -h host.docker.internal -U postgres
```

Optional

```sql
CREATE USER retail WITH PASSWORD 'retail';
GRANT ALL PRIVILEGES ON SCHEMA public TO retail;
```
Quit psql

```shell
\q
```


# RabbitMQ

```shell
docker run --name rabbitmq --hostname localhost -it  -p 5672:5672 -e RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS="-rabbitmq_stream advertised_host localhost -rabbitmq_stream advertised_port 5552 -rabbitmq_stream tcp_listeners [5552]" -e RABBITMQ_PLUGINS="rabbitmq_stream,rabbitmq_management"  -p 5552:5552 -p 15672:15672  -p  1884:1884 --rm bitnami/rabbitmq:3.11.13
```


```shell
docker run --name rabbitmq --hostname localhost -it -p 5672:5672 -e RABBITMQ_PLUGINS="rabbitmq_stream,rabbitmq_management"  -p 5552:5552 -p 15672:15672  -p  1884:1884 --rm bitnami/rabbitmq:3.11.13
```

RABBITMQ_USERNAME: user
RABBITMQ_PASSWORD: bitnami

# Start Web Application

Run application

mvn -Dmaven.test.skip=true package


```shell
java -jar applications/retail-analytics-app/target/retail-analytics-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=local --spring.datasource.username=postgres  --spring.datasource.password=password123 --spring.rabbitmq.username=user --spring.rabbitmq.password=bitnami --spring.data.redis.cluster.nodes=0.0.0.0:6379
```

Expected Output

```shell  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 ...
```

# Testing Load Products


```shell
open http://localhost:15672
```

Steps

- Login with default user/bitnami
- Goto Exchanges -> retail.products
- REQUIRED: Add header
  - contentType=application/json

Past Content from [Product.json](https://raw.githubusercontent.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/main/scripts/generate_customer_orders/resources/products.json)

- Click Publish Message

This will load products into Postgres

# Customer Orders

```shell
open http://localhost:15672
```

Steps

- Login with default guest/guest
- Goto Exchanges -> retail.customer.orders
- Click publish message



Publish the following JSON an order



- Buy Milk, Peanut and butter

Replace $USERNAME at needed

```json
  {"id":4,"customerIdentifier":{"customerId":"$USERNAME"},
  "productOrders":[
    {"productId":"sku4","quantity":1},
    {"productId":"sku1","quantity":1},
    {"productId":"sku2","quantity":1}
  ]}
```
Example

```json
  {"id":4,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku4","quantity":1},
    {"productId":"sku1","quantity":1},
    {"productId":"sku2","quantity":1}
  ]}
```

Verify Products in Postgres psql with retail login user

```shell
docker run -it --rm \
    bitnami/postgresql:15.2.0 psql -h docker.internal.host -U postgres -d postgres
```

```sql
 select * from products;
```

----------------------------
## Reload Products from stream

In psql

```sql
 delete from products;
```

Verify deleted (0 rows)

```sql
select * from products;
```


Kill analytics application


Run application with replay flag

```shell
java -jar applications/retail-analytics-app/target/retail-analytics-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=local --spring.datasource.username=postgres  --spring.datasource.password=password123 --spring.rabbitmq.username=user --spring.rabbitmq.password=bitnami --spring.data.redis.cluster.nodes=0.0.0.0:6379 --rabbitmq.streaming.replay=true
```

Verify producted reloaded from RabbitMQ

```sql
select * from products;
```


--------------------
### Buy Peanut Butter and Jelly

- Goto Exchanges -> retail.customer.orders


```json
  {"id":1,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku1","quantity":1},
    {"productId":"sku2","quantity":1}
  ]}
```

- Click publish message

### Buy Bread  

- Goto Exchanges -> retail.customer.orders
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

```json
  {"id":3,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku4","quantity":1}
  ]}
```

Recommendation should include Peanut butter/Jelly





