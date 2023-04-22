# Start App


```shell
java -jar applications/retail-web-app/target/retail-web-app-0.0.1-SNAPSHOT.jar --retail.customer.id=nyla  --server-port=0

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