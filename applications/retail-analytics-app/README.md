# Postgres

```shell
psql -d postgres -U retail
```

# Useful SQL

List connections
```
 SELECT usename,application_name,pid FROM pg_stat_activity;
```

Get current connection information

```
\conninfo
```


```shell
CREATE USER retail WITH PASSWORD 'retail';
create schema retail;
ALTER USER retail SET search_path TO retail;
```

```shell
GRANT ALL PRIVILEGES ON SCHEMA public TO retail;
GRANT ALL PRIVILEGES ON SCHEMA retail TO retail;
```

```shell
GRANT ALL PRIVILEGES ON TABLE products TO gregoryg;
```

```shell
select * from pg_tables where tablename = 'products';
```

## Docker building image

```shell
mvn install
cd applications/retail-analytics-app
mvn spring-boot:build-image
```

```shell
docker tag retail-analytics-app:0.0.1-SNAPSHOT cloudnativedata/retail-analytics-app:0.0.1-SNAPSHOT
docker push cloudnativedata/retail-analytics-app:0.0.1-SNAPSHOT
```


--------------

Testing


```json
{
  "customerId" : "nyla"
  
}

```