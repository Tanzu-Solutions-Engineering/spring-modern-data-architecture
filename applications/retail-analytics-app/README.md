
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
GRANT ALL PRIVILEGES ON SCHEMA public TO 'retail';
```

```shell
GRANT ALL PRIVILEGES ON TABLE products TO gregoryg;
```

```shell
select * from pg_tables where tablename = 'products';
```