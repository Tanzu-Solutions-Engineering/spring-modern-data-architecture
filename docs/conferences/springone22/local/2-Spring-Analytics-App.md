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

TODO: Replace with database migration

```shell
psql -d postgres -U retail -f applications/retail-analytics-app/src/main/resources/db/schema_creation.sql
```


# Start Web Application

## Build applications


From root directory

```shell
./mvnw package
```


Set Encrypted Password in file or environment variable

```shell
export SPRING_DATASOURCE_PASSWORD=`java -classpath deployments/lib/nyla.solutions.core-2.0.0-SNAPSHOT.jar -DCRYPTION_KEY=CHANGEMEKEY  nyla.solutions.core.util.Cryption retail`
```

Run application

```shell
java -DCRYPTION_KEY=CHANGEMEKEY -jar applications/retail-analytics-app/target/retail-analytics-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```


# Testing Load Products


```shell
open http://localhost:15672
```

Steps

- Login with default guest/guest
- Goto Exchanges -> retail.saveProductConsumer
- Click publish message



```json
[
  {"id" : "sku1", "name" : "Peanut butter"},
  {"id" : "sku2", "name" : "Jelly"},
  {"id" : "sku3", "name" : "Bread"},
  {"id" : "sku4", "name" : "Milk"}
]
```

# Testing Customer Orders

```shell
open http://localhost:15672
```

Steps

- Login with default guest/guest
- Goto Exchanges -> retail.orderConsumer
- Click publish message





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

- Buy Peanut Butter and Jelly
- 
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



