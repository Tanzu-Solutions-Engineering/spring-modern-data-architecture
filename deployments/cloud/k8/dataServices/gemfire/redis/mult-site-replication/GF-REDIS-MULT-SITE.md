```shell
kubectl apply -f https://projectcontour.io/quickstart/contour-gateway-provisioner.yaml 
```


```shell
kubectl --namespace projectcontour get deployments
```


```shell
k apply -f deployments/cloud/k8/dataServices/gemfire/redis/mult-site-replication/gf-gatewayclass-and-gateway.yaml
```



```shell
k apply -f deployments/cloud/k8/dataServices/gemfire/redis/mult-site-replication/gf-multi-site-redis.yaml
```


Testing

```shell
k port-forward gf-redis-server-0 6379:6379
```


```shell
kubectl exec -it gf-redis-locator-0 -- gfsh
```


```shell
connect --locator=gf-redis-locator-0.gf-redis-locator.default.svc.cluster.local[10334]
```


