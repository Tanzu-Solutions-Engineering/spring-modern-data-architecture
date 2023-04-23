
# Start Postgres

```shell
docker run -p 5432:5432 -d  --expose 5432 --name postgresql --rm -e POSTGRESQL_PASSWORD=password123  -h postgresql bitnami/postgresql:15.2.0
```


Create user

```shell
docker run -it --rm \
    --network host \
    bitnami/postgresql:15.2.0 psql -h 0.0.0.0 -U postgres
```

```shell
docker run -it --rm \
    bitnami/postgresql:15.2.0 psql -h host.docker.internal -U postgres
```


```sql
CREATE USER retail WITH PASSWORD 'retail';
GRANT ALL PRIVILEGES ON SCHEMA public TO retail;
```
Quit psql

```shell
\q
```


Login as retail user

```shell
docker run -it --rm \
    bitnami/postgresql:15.2.0 psql -h host.docker.internal -d postgres -U retail 
```

Verify database access in psql

```shell
\dt
```


Sample output

```shell
postgres=> \l
                                                 List of databases
   Name    |  Owner   | Encoding |   Collate   |    Ctype    | ICU Locale | Locale Provider |   Access privileges   
-----------+----------+----------+-------------+-------------+------------+-----------------+-----------------------
 postgres  | postgres | UTF8     | en_US.UTF-8 | en_US.UTF-8 |            | libc            | 
 template0 | postgres | UTF8     | en_US.UTF-8 | en_US.UTF-8 |            | libc            | =c/postgres          +
           |          |          |             |             |            |                 | postgres=CTc/postgres
 template1 | postgres | UTF8     | en_US.UTF-8 | en_US.UTF-8 |            | libc            | =c/postgres          +
           |          |          |             |             |            |                 | postgres=CTc/postgres
(3 rows)

```


# RabbitMQ




```shell
docker run --name rabbitmq --hostname localhost -it -p 5672:5672 -e RABBITMQ_SERVER_ADDITIONAL_ERL_ARGS="-rabbitmq_stream advertised_host localhost -rabbitmq_stream advertised_port 5552 -rabbitmq_stream tcp_listeners [5552]" -e RABBITMQ_PLUGINS="rabbitmq_stream,rabbitmq_management"  -p 5552:5552 -p 15672:15672  -p  1884:1884 --rm bitnami/rabbitmq:3.11.13
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
 :: Spring Boot ::                (v2.7.4)


2022-11-03 14:49:14.847  INFO 88078 --- [           main] c.v.retail.analytics.RetailAnalyticsApp  : Starting RetailAnalyticsApp v0.0.1-SNAPSHOT using Java 17.0.4.1 on gregoryg-a02.vmware.com with PID 88078 (/Users/Projects/VMware/Tanzu/Tan/Users/Projects/VMware/Tanzu/Tn-data-architecture/applicationsanzuData/Spring/dev/spring-modern-data-architecture/applications/retail-analytics-app/target/retail-analytics-app-0.0.1u/TanzuData/Spring/dev/spring-modern-data-architecture)
...
tics-app'
2022-11-03 14:49:42.464  INFO 88078 --- [           main] c.v.retail.analytics.RetailAnalyticsApp  : Started RetailAnalyticsApp in 29.406 seconds (JVM running for 31.094)

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

```json
[
  {"id" : "sku1", "name" : "Peanut butter"},
  {"id" : "sku2", "name" : "Jelly"},
  {"id" : "sku3", "name" : "Bread"},
  {"id" : "sku4", "name" : "Milk"}
]
```

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
    bitnami/postgresql:15.2.0 psql -h 0.0.0.0 -U retail -d postgres
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
java -jar applications/retail-analytics-app/target/retail-analytics-app-0.0.1-SNAPSHOT.jar --rabbitmq.streaming.replay=true --spring.profiles.active=local 
```

Verify producted reloaded from RabbitMQ

```sql
select * from products;
```


--------------------
- Buy Peanut Butter and Jelly

```json
  {"id":1,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku1","quantity":1},
    {"productId":"sku2","quantity":1}
  ]}
```

- Buy Bread  
-
```json
  {"id":2,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku3","quantity":1}
  ]}
```


- Buy Milk

```json
  {"id":3,"customerIdentifier":{"customerId":"nyla"},
  "productOrders":[
    {"productId":"sku4","quantity":1}
  ]}
```




