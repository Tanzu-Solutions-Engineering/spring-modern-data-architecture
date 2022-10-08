
# Useful SQL

List connections
```
 SELECT usename,application_name,pid FROM pg_stat_activity;
```

Get current connection information

```
\conninfo
```