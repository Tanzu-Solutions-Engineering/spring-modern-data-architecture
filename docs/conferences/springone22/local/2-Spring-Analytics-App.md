# Start RabbitMQ


Start RabbitMQ

```shell
brew services start rabbitmq
```

Verify RabbitMQ running


```shell
 brew services list 
```

Example output

```shell
brew services list          
Name     Status  User     File
rabbitmq started gregoryg ~/Library/LaunchAgents/homebrew.mxcl.rabbitmq.plist

```


# Start Postgres

```shell
brew services start postgresql@14
```

Login
```shell
psql -d postgres -U postgres 
```

Create user

```shell
CREATE USER retail WITH PASSWORD 'retail';
GRANT ALL PRIVILEGES ON SCHEMA public TO 'retail';
```
Quit psql

```shell
\q
```


Login as retail user

```shell
psql -d postgres -U retail 
```

Verify database access in psql

```shell
\dt
```


Sample output

```shell
postgres=> \l
                             List of databases
   Name    |  Owner   | Encoding | Collate | Ctype |   Access privileges   
-----------+----------+----------+---------+-------+-----------------------
 postgres  | gregoryg | UTF8     | C       | C     | 
 template0 | gregoryg | UTF8     | C       | C     | =c/gregoryg          +
           |          |          |         |       | gregoryg=CTc/gregoryg
 template1 | gregoryg | UTF8     | C       | C     | =c/gregoryg          +
           |          |          |         |       | gregoryg=CTc/gregoryg


```

# Start Web Application

Run application

```shell
java -jar applications/retail-analytics-app/target/retail-analytics-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```

Expected Output

```shell
java -jar applications/retail-analytics-app/target/retail-analytics-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=local

  .   ____          _            __ _ _
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

- Login with default guest/guest
- Goto Exchanges -> retail.products
- REQUIRED: Add Header
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


Verify Products in Postgres psql with retail login user

```shell
psql -d postgres -U retail 
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




