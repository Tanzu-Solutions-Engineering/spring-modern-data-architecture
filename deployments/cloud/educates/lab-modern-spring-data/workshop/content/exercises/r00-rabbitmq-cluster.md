# Start RabbitMQ 

[ Description of Activity ]

## RabbitMQ Cluster Operator
[ Description of Item & Task ]

### Install the RabbitMQ Operator
* Already Complete
* Create namespace "rabbitmq-system" stored

``` 
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

**See all resources for RabbitMQ Cluster Operator**
```terminal:execute
k get all -l app.kubernetes.io/name=rabbitmq-cluster -n {{ ENV_RMQ_KUB_NAMESPACE }}
session: rabbitmq
```

## RabbitMQ Cluster 
[ Description of Item & Task ]

### Create a RabbitMQ Cluster?

1. Create Cluster using a .yaml file

    ```terminal:execute
    kubectl apply -f ~/templates/rabbitmq/{{ ENV_RABBITMQ_SERVER_NAME }}.yaml
    session: rabbitmq
    ```

2. Check if pod has been created successfully

    ```terminal:execute
    kubectl get pod/{{ ENV_RABBITMQ_SERVER_NAME }}-server-0 -n {{ ENV_RMQ_KUB_NAMESPACE }}
    session: rabbitmq
    ```

3. Wait until pod is ready

    ```terminal:execute
    kubectl wait pods -n {{ ENV_RMQ_KUB_NAMESPACE }} {{ ENV_RABBITMQ_SERVER_NAME }}-server-0 --for condition=Ready --timeout=90s
    session: rabbitmq
    ```
4. View all resources created
    ```terminal:execute
    kubectl get all -l app.kubernetes.io/name={{ ENV_RABBITMQ_SERVER_NAME }}
    session: rabbitmq
    ```

 
