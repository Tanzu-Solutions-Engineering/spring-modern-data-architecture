# Start GemFire


```shell
cd ~/spring-modern-data-architecture/data-services/gemfire/vmware-gemfire-9.15.0/bin
cp -r ~/spring-modern-data-architecture/data-services/gemfire/gemfire-for-redis-apps-1.0.1 /tmp
 ./gfsh
```
<<<<<<< HEAD

In Gfsh

=======
**Note:** What to expect in terminal after this?


In Gfsh
>>>>>>> main
```shell
start locator --name=locator --locators=127.0.0.1[10334] --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
start server --name=redisServer1   --locators=127.0.0.1[10334]  --server-port=40404 --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1 --start-rest-api=true --http-service-bind-address=127.0.0.1 --http-service-port=9090  --J=-Dgemfire-for-redis-port=6379 --J=-Dgemfire-for-redis-enabled=true --classpath=/tmp/gemfire-for-redis-apps-1.0.1/lib/*  
```

**Note:** What to expect in terminal?


Example output

```shell
start server --name=redisServer1   --locators=127.0.0.1[10334]  --server-port=40404 --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1 --start-rest-api=true --http-service-bind-address=127.0.0.1 --http-service-port=9090  --J=-Dgemfire-for-redis-port=6379 --J=-Dgemfire-for-redis-enabled=true --classpath=/tmp/gemfire-for-redis-apps-1.0.1/lib/*
Starting a Geode Server in /Users/gregoryg/spring-modern-data-architecture/data-services/gemfire/vmware-gemfire-9.15.0/bin/redisServer1...
....geode-server-all-1.15.0.jar
Server in /Users/gregoryg/spring-modern-data-architecture/data-services/gemfire/vmware-gemfire-9.15.0/bin/redisServer1 on 192.168.1.76[40404] as redisServer1 is currently online.
....
gfsh>
```

In a new window
# Start Web Application

## Build applications


From root directory

```shell
./mvnw package
```

Run application

```shell
cd /Users/Projects/VMware/Tanzu/TanzuData/Spring/dev/spring-modern-data-architecture
java -jar applications/retail-web-app/target/retail-web-app-0.0.1-SNAPSHOT.jar --retail.customer.id=$USERNAME
```


Example expected output

```shell

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.3)
....
2022-11-03 14:22:35.727  INFO 78671 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-11-03 14:22:35.727  INFO 78671 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 9370 ms
2022-11-03 14:22:42.247  INFO 78671 --- [           main] o.s.b.a.w.s.WelcomePageHandlerMapping    : Adding welcome page: class path resource [static/index.html]
...
2022-11-03 14:35:16.891  INFO 83584 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''

```

Open Browser


```shell
open http://localhost:8080
```

Post Customer Favorites

-- NOTE: Replace $USERNAME to username started with the application


```shell
curl -X 'POST' \
  'http://localhost:8080/customer/favorites/favorite' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "$USERNAME",
  "favorites": [
    {
      "product": {
        "id": "sku4",
        "name": "Milk"
      },
      "quantity": 1
    },
   {
      "product": {
        "id": "sku3",
        "name": "Bread"
      },
      "quantity": 1
    },{
     "product": {
        "id": "sku1",
        "name": "Peanut butter"
      },
      "quantity": 1
    }
  ]
}'
```

View Customer Favorites on app

Post Promotions


```shell
curl -X 'POST' \
  'http://localhost:8080/promotions/promotion/publish' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "$USERNAME",
  "marketingMessage": "string",
  "products": [
    {
      "id": "sku2",
      "name": "Jelly"
    },
    {
      "id": "sku4",
      "name": "Milk"
    }
  ]
}'
```

View promotions on app