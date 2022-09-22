# Start GemFire


```shell

```
cd ~/spring-modern-data-architecture/data-services/gemfire/vmware-gemfire-9.15.0/bin
./gfsh




```shell
 ./gfsh
```

In Gfsh

```shell
start locator --name=locator --locators=127.0.0.1[10334] --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1  --jmx-manager-hostname-for-clients=127.0.0.1 --http-service-bind-address=127.0.0.1
start server --name=redisServer1   --locators=127.0.0.1[10334]  --server-port=40404 --bind-address=127.0.0.1 --hostname-for-clients=127.0.0.1 --start-rest-api=true --http-service-bind-address=127.0.0.1 --http-service-port=9090  --J=-Dgemfire-for-redis-port=6379 --J=-Dgemfire-for-redis-enabled=true --classpath=<HOME-DIRECTORY-HERE>/spring-modern-data-architecture/data-services/gemfire/gemfire-for-redis-apps-1.0.1/lib/*  
```
