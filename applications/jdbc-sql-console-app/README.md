# jdbc-sql-console-app


Spring Boot application to perform SQL on an a given database


## Configuration

Adding the following spring properties

```shell
java -jar target/jdbc-sql-console-app-0.0.1-SNAPSHOT.jar --spring.datasource.url="jdbc:mysql://<HOST>:<PORT>/mysql" --spring.datasource.username=${USER} --spring.datasource.password=${MYSQL_DB_PASSWORD}
```

## Tutorial 

You access as Swagger UI to executed query

```shell
open http://localhost:7280
```

Example Query using CURL

```shell
curl -X 'POST' \
'http://localhost:7280/query' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d 'SELECT * FROM customer_orders limit 10'
```

-----------------------------------------------------------
## Docker building image

```shell
mvn install
cd applications/jdbc-sql-console-app
mvn package
docker build  --platform linux/amd64,linux/arm64 -t jdbc-sql-console-app:0.0.2-SNAPSHOT .
```

```shell
docker tag jdbc-sql-console-app:0.0.2-SNAPSHOT cloudnativedata/jdbc-sql-console-app:0.0.2-SNAPSHOT
docker push cloudnativedata/jdbc-sql-console-app:0.0.2-SNAPSHOT
```