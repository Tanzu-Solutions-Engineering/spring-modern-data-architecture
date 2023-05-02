# Run App


```shell
java -jar applications/retail-source-app/target/retail-source-app-0.0.1-SNAPSHOT.jar
```

## Docker building image

```shell
mvn install
cd applications/retail-source-app
mvn spring-boot:build-image
```

```shell
docker tag retail-source-app:0.0.1-SNAPSHOT cloudnativedata/retail-source-app:0.0.1-SNAPSHOT
docker push cloudnativedata/retail-source-app:0.0.1-SNAPSHOT
```