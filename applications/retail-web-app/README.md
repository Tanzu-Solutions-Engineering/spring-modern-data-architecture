# Running retail-web-app



Prerequiste 

Start Redis App


Locator



```shell
java --add-opens java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-opens java.base/sun.nio.ch=ALL-UNNAMED -jar applications/spring-gf-locator/target/spring-gf-locator-0.0.1-SNAPSHOT.jar --server.port=0
```

Server 1
```shell
java  --add-opens java.base/sun.nio.ch=ALL-UNNAMED -jar applications/spring-gf-redis-server/target/spring-gf-redis-server-0.0.1-SNAPSHOT.jar --server.port=0 --spring.data.gemfire.cache.server.port=10001 --spring.data.gemfire.name=server1 --spring.data.gemfire.locators="localhost[10334]" --gemfire-for-redis-port=6379
```

Server 2
```shell
java  --add-opens java.base/sun.nio.ch=ALL-UNNAMED -jar applications/spring-gf-redis-server/target/spring-gf-redis-server-0.0.1-SNAPSHOT.jar --server.port=0 --spring.data.gemfire.cache.server.port=10002 --spring.data.gemfire.name=server2 --spring.data.gemfire.locators="localhost[10334]" --gemfire-for-redis-port=6372
```

```shell
java -jar applications/retail-web-app/target/retail-web-app-0.1.0-SNAPSHOT.jar --spring.profile.active=gemfire --spring.data.gemfire.pool.default.locators=localhost[10334] --retail.customer.id=nyla
```



## Docker building image

```shell
mvn install
cd applications/retail-web-app
mvn spring-boot:build-image
```

```shell
docker tag retail-web-app:0.1.0-SNAPSHOT cloudnativedata/retail-web-app:0.1.0-SNAPSHOT
docker push cloudnativedata/retail-web-app:0.1.0-SNAPSHOT
```