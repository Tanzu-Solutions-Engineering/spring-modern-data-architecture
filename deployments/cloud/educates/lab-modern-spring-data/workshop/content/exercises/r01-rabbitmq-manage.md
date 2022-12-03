# RabbitMQ Management UI
[ Description of Activity ]

## Log in to the RabbitMQ Management UI
[ Description of Task ]

### Get your Username

1. Store username in an environment variable

    ```terminal:execute
    command: export RMQ_USERNAME="$(kubectl get secret {{ ENV_RABBITMQ_SERVER_NAME }} -n {{ENV_RMQ_KUB_NAMESPACE}} -default-user -o jsonpath='{.data.username}' | base64 --decode)"
    session: rabbitmq
    ```

2. View username value

    ```terminal:execute
    command: echo "username: $RMQ_USERNAME"
    session: rabbitmq
    ```

3. Copy username value to clipboard

    ```workshop:copy
    text: echo "$RMQ_USERNAME"
    ```

### Get your Password

1. Store password in an environment variable

    ```terminal:execute 
    command: export RMQ_PASSWORD="$(kubectl get secret {{ ENV_RABBITMQ_SERVER_NAME }} -n {{ENV_RMQ_KUB_NAMESPACE}} -default-user -o jsonpath='{.data.password}' | base64 --decode)"
    session: rabbitmq
    ```

2. View password value

    ```terminal:execute
    command: echo "password: $RMQ_PASSWORD"
    session: rabbitmq
    ```

3. Copy password to clipboard

    ```workshop:copy
    text: echo "$RMQ_PASSWORD"
    ```

## Start RabbitMQ Management UI
[ Description of Task ]

```terminal:execute
command: kubectl port-forward "service/{{ ENV_RABBITMQ_SERVER_NAME }}" 15672 -n {{ ENV_RMQ_KUB_NAMESPACE }}
session: rabbitmq
```

### Open RMQ Management UI

[ Description of Task ]

```dashboard:open-dashboard
name: RabbitMQ
```