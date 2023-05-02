

k create namespace demo-data
kubectl config set-context --current --namespace=demo-data

----------

Install GemFire

```shell
cd deployments/cloud/k8/dataServices/gemfire/
./gf-k8-setup.sh
```

```shell
k apply -f deployments/cloud/k8/dataServices/gemfire/redis/gf-redis.yaml
```


# Private Cloud

```shell
k apply -f deployments/cloud/k8/dataServices/rabbitmq/vmware-rabbitmq-1-node.yml
```
