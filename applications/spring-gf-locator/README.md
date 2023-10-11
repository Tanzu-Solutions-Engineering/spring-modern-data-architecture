# Start App


```shell
export JAVA_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-exports  java.management/com.sun.jmx.remote.security=ALL-UNNAMED --add-exports  java.base/sun.nio.ch=ALL-UNNAMED"
```


```shell
java -jar applications/spring-gf-locator/target/spring-gf-locator-0.0.1-SNAPSHOT.jar
```


Register MVn packages

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