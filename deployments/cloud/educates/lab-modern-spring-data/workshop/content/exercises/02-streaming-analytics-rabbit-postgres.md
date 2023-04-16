# Start RabbitMQ 

## RabbitMQ Cluster Operator

### Install the RabbitMQ Operator
* Already Complete
* Create namespace "rabbitmq-system" stored
The RabbitMQ Cluster operator is already installed on your machine! Below is the command used to install it.

``` 
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

**See all resources for RabbitMQ Cluster Operator**

```terminal:execute
command: k get pods -n rabbitmq-system
session: rabbitmq
```

## RabbitMQ Cluster 

### Create a RabbitMQ Cluster

1. Create Cluster using a yaml file
    1. Review Cluster yaml file

        ```editor:open-file 
        file: ~/data-services/rabbitmq/rabbitmq-cluster.yaml 
        ```

        [ Description of file contents ]

        This file will create a RabbitmqCluster in the default namespace.

    2. Create a cluster from the .yaml file

        ```terminal:execute 
        command: k apply -f ~/data-services/rabbitmq/rabbitmq-cluster.yaml  
        session: rabbitmq 
        ```

2. Check if pod has been created successfully

    ```terminal:execute 
    command: kubectl get pod/rabbitmq-server-0 
    session: rabbitmq 
    ```

3. Wait until pod is ready

    ```terminal:execute 
    command: kubectl wait pod/rabbitmq-server-0 --for condition=Ready --timeout=90s 
    session: rabbitmq 
    ```

4. Start Database

```terminal:execute 
command: k apply -f ~/data-services/postgres/postgres.yml
session: postgres 
```