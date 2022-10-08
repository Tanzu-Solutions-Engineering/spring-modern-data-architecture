# Start Postgres

```shell
brew services start postgresql@14
```

Login
```shell
psql -d postgres -U postgres 
```

Create user

```shell
CREATE USER retail WITH PASSWORD 'retail';
GRANT ALL PRIVILEGES ON SCHEMA public TO 'retail';
```
Quit psql

```shell
\q
```

Login as retail user

TODO: Replace with database migration

```shell
psql -d postgres -U retail -f applications/retail-analytics-app/src/main/resources/db/schema_creation.sql
```


# Start Web Application

## Build applications


From root directory

```shell
./mvnw package
```


Set Encrypted Password in file or environment variable

```shell
export SPRING_DATASOURCE_PASSWORD=`java -classpath deployments/lib/nyla.solutions.core-2.0.0-SNAPSHOT.jar -DCRYPTION_KEY=CHANGEMEKEY  nyla.solutions.core.util.Cryption retail`
```

Run application

```shell
java -DCRYPTION_KEY=CHANGEMEKEY -jar applications/retail-analytics-app/target/retail-analytics-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=local
```


# Testing Customer Favorites

```shell
open http://localhost:15672
```

Steps

- Login with default guest/guest
- Goto Exchanges -> retail.calculateFavorites
- Click publish message

Publish the following JSON

```json
 { "customerId" : "nyla"}
```




