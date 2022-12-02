# Start RabbitMQ 

[ Description of Activity ]

## RabbitMQ Cluster Operator
[ Description of Item & Task ]

### Install the RabbitMQ Operator
* Already COmplete
* Create namespace "rabbitmq-system" stored
``` 
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

```terminal:execute
k get all -l app.kubernetes.io/name=rabbitmq-cluster -n {{ RMQ_KUB_NAMESPACE }}
session: rabbitmq
```

## RabbitMQ Cluster 
[ Description of Item & Task ]

### Create a RabbitMQ Cluster?
```terminal:execute
kubectl get all -l app.kubernetes.io/name={{ RABBITMQ_SERVER_NAME }}
session: rabbitmq
```
 
### Start RabbitMQ Management UI
[ Description of Task ]
```terminal:execute
command: kubectl port-forward "service/{{ RABBITMQ_SERVER_NAME }}" 15672
session: rabbitmq
```

### Log in to the RabbitMQ Management UI
[ Description of Task ]

**Username:**
```terminal:execute
export RMQ_USERNAME="$(kubectl get secret {{ RABBITMQ_SERVER_NAME }}-default-user -o jsonpath='{.data.username}' | base64 --decode)"
echo "username: $RMQ_USERNAME"
session:rabbitmq
```

**Password:** 
```terminal:execute 
export RMQ_PASSWORD="$(kubectl get secret {{ RABBITMQ_SERVER_NAME }}-default-user -o jsonpath='{.data.password}' | base64 --decode)"
echo "password: $RMQ_PASSWORD"
session:rabbitmq
```

### Open RMQ Management UI
[ Description of Task ]

```dashboard:open-dashboard
name: RabbitMQ
```