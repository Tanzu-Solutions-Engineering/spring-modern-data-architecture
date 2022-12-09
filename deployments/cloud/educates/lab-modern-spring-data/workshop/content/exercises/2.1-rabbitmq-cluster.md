# Start RabbitMQ 

[ Description of Activity ]

## RabbitMQ Cluster Operator
[ Description of Item & Task ]

### Install the RabbitMQ Operator
* Already Complete
* Create namespace "rabbitmq-system" stored
The RabbitMQ Cluster operator is already installed on your machine! Below is the command used to install it.

``` 
kubectl apply -f "https://github.com/rabbitmq/cluster-operator/releases/latest/download/cluster-operator.yml"
```

**See all resources for RabbitMQ Cluster Operator**

```terminal:execute
command: kubectl get all -l app.kubernetes.io/name=rabbitmq-cluster-operator -n {{ ENV_RMQ_KUB_NAMESPACE }}
session: rabbitmq
```

## RabbitMQ Cluster 
[ Description of Item & Task ]

### Create a RabbitMQ Cluster

1. Create Cluster using a yaml file
    1. Review Cluster yaml file

        ```editor:open-file 
        file: ~/templates/data-services/rabbitmq/{{ ENV_RABBITMQ_SERVER_NAME }}.yaml 
        ```

        [ Description of file contents ]

        This file will create a RabbitmqCluster called "{{ ENV_RABBITMQ_SERVER_NAME }}" in the namespace "{{ ENV_RMQ_KUB_NAMESPACE }}".

    2. Create a cluster from the .yaml file

        ```terminal:execute 
        command: kubectl apply -f ~/templates/data-services/rabbitmq/{{ ENV_RABBITMQ_SERVER_NAME }}.yaml 
        session: rabbitmq 
        ```

2. Check if pod has been created successfully

    ```terminal:execute 
    command: kubectl get pod/{{ ENV_RABBITMQ_SERVER_NAME }}-server-0 -n {{ ENV_RMQ_KUB_NAMESPACE }} 
    session: rabbitmq 
    ```

3. Wait until pod is ready

    ```terminal:execute 
    command: kubectl wait pods -n {{ ENV_RMQ_KUB_NAMESPACE }} {{ ENV_RABBITMQ_SERVER_NAME }}-server-0 --for condition=Ready --timeout=90s 
    session: rabbitmq 
    ```

4. View all resources created for cluster

    ```terminal:execute 
    command: kubectl get all -l app.kubernetes.io/name={{ ENV_RABBITMQ_SERVER_NAME }} 
    session: rabbitmq 
    ```

