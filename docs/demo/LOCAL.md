
```shell
start locator --name=locator
```

```shell
start server --name=redisServer1 --locators=localhost[10334] --server-port=40404 --J=-Dgemfire-for-redis-enabled=true
```

```shell
start server --name=redisServer2 --locators=localhost[10334] --server-port=40405 --J=-Dgemfire-for-redis-port=6380 --J=-Dgemfire-for-redis-enabled=true
```

```shell
start server --name=redisServer3 --locators=localhost[10334] --server-port=40406 --J=-Dgemfire-for-redis-port=6383 --J=-Dgemfire-for-redis-enabled=true
```