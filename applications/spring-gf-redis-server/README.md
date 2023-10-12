
Download


GemFire
VMware GemFire for Redis Apps for GemFire 9.15

https://network.tanzu.vmware.com/products/tanzu-gemfire-for-redis-apps/


# Start App

Server 1
```shell
mkdir -p  runtime/server1
cd runtime/server1
java --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-exports  java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-exports  java.base/sun.nio.ch=ALL-UNNAMED -jar /Users/Projects/VMware/Tanzu/TanzuData/Spring/dev/spring-modern-data-architecture/applications/spring-gf-redis-server/target/spring-gf-redis-server-0.1.0-SNAPSHOT.jar --spring.config.location=file:/Users/Projects/VMware/Tanzu/TanzuData/Spring/dev/spring-modern-data-architecture/applications/spring-gf-redis-server/src/main/resources/application.yml
```

Server 2
```shell
mkdir -p  runtime/server2
cd runtime/server2
java --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-exports  java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-exports  java.base/sun.nio.ch=ALL-UNNAMED -jar /Users/Projects/VMware/Tanzu/TanzuData/Spring/dev/spring-modern-data-architecture/applications/spring-gf-redis-server/target/spring-gf-redis-server-0.1.0-SNAPSHOT.jar --spring.profiles.active=server2 --spring.config.location=file:/Users/Projects/VMware/Tanzu/TanzuData/Spring/dev/spring-modern-data-architecture/applications/spring-gf-redis-server/src/main/resources/application-server2.yaml --gemfire.gemfire-for-redis-port=63792
```

Server 3
```shell
mkdir -p  runtime/server3
cd runtime/server3
java --add-opens java.base/sun.nio.ch=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-exports  java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-exports  java.base/sun.nio.ch=ALL-UNNAMED -jar /Users/Projects/VMware/Tanzu/TanzuData/Spring/dev/spring-modern-data-architecture/applications/spring-gf-redis-server/target/spring-gf-redis-server-0.1.0-SNAPSHOT.jar --spring.profiles.active=server3 --spring.config.location=file:/Users/Projects/VMware/Tanzu/TanzuData/Spring/dev/spring-modern-data-architecture/applications/spring-gf-redis-server/src/main/resources/application-server3.yaml --server-port=0 --gemfire.gemfire-for-redis-port=63793
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
