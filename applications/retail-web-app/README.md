
## Docker building image

```shell
mvn install
cd applications/retail-web-app
mvn spring-boot:build-image
```

```shell
docker tag retail-web-app:0.0.1-SNAPSHOT cloudnativedata/retail-web-app:0.0.1-SNAPSHOT
docker push cloudnativedata/retail-web-app:0.0.1-SNAPSHOT
```