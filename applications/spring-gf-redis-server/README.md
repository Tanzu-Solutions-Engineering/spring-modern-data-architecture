# Start App


Start with Locator

```shell
java  --add-opens java.base/sun.nio.ch=ALL-UNNAMED -jar applications/spring-gf-redis-server/target/spring-gf-redis-server-0.0.1-SNAPSHOT.jar --server.port=0 --spring.data.gemfire.cache.server.port=10001 --spring.data.gemfire.name=server1 --spring.data.gemfire.locators="localhost[10334]" --gemfire-for-redis-port=6379
```

Without locator

```shell
java  -jar applications/spring-gf-redis-server/target/spring-gf-redis-server-0.0.1-SNAPSHOT.jar --server.port=0 --spring.data.gemfire.cache.server.port=10001 --spring.data.gemfire.name=server1 --gemfire-for-redis-port=6379
```


Register 

```shell
mvn install:install-file \
-Dfile=/Users/devtools/repositories/IMDG/gemfire/gemfire-for-redis-apps-1.0.1/lib/gemfire-for-redis-apps-1.0.1.jar \
-DgroupId=com.vmware.gemfire \
-DartifactId=gemfire-for-redis-apps \
-Dversion=1.0.1 \
-Dpackaging=jar \
-DgeneratePom=true
```

Docker

```shell
cd applications/spring-gf-redis-server
mvn spring-boot:build-image

```


```shell script
docker tag spring-gf-redis-server:0.0.1-SNAPSHOT cloudnativedata/spring-gf-redis-server:0.0.1-SNAPSHOT 
docker login
docker push cloudnativedata/spring-gf-redis-server:0.0.1-SNAPSHOT

```