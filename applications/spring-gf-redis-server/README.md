# Start App


Without locator

```shell
java  -jar applications/spring-gf-redis-server/target/spring-gf-redis-server-0.0.3-SNAPSHOT.jar --server.port=0 --spring.data.gemfire.cache.server.port=10001 --spring.data.gemfire.name=server1 --gemfire-for-redis-port=6379
```


Register 

```shell
mvn install:install-file \
-Dfile=/Users/devtools/repositories/IMDG/gemfire/vmware-gemfire-for-redis-apps-1.1.0/lib/gemfire-for-redis-apps-1.1.0.jar \
-DgroupId=com.vmware.gemfire \
-DartifactId=gemfire-for-redis-apps \
-Dversion=1.1.0 \
-Dpackaging=jar \
-DgeneratePom=true
```

Docker

```shell
cd applications/spring-gf-redis-server
mvn clean spring-boot:build-image

```


```shell script
docker tag spring-gf-redis-server:0.0.3-SNAPSHOT cloudnativedata/spring-gf-redis-server:0.0.3-SNAPSHOT 
docker login
docker push cloudnativedata/spring-gf-redis-server:0.0.3-SNAPSHOT

```

# Run Docker


```shell
docker  run -p 6379:6379 --name gf-redis -e gemfire-for-redis-port=6379 cloudnativedata/spring-gf-redis-server:0.0.3-SNAPSHOT 
```
