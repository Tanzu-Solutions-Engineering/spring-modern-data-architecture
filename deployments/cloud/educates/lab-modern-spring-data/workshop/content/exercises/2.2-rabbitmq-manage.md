# RabbitMQ Management UI
[ Description of Activity ]

## Log in to the RabbitMQ Management UI
[ Description of Task ]

Login information is stored as Kubernetes secrets...


```terminal:execute
command: kubectl get secrets -n {{ENV_RMQ_KUB_NAMESPACE}}
session: rabbitmq
```

### Get RabbitMQ Management UI Username
1. Store username in an environment variable

    ```terminal:execute 
    command: RMQ_USERNAME="$(kubectl get secret {{ ENV_RABBITMQ_SERVER_NAME }}-default-user -n {{ENV_RMQ_KUB_NAMESPACE}} -o jsonpath='{.data.username}' | base64 --decode)" 
    session: rabbitmq 
    ```

2. View username value

    ```terminal:execute 
    command: echo "username => $RMQ_USERNAME" 
    session: rabbitmq 
    ```

3.  Copy username value and paste in the file linked below

    ```editor:open-file
    file: ~/templates/data-services/rabbitmq/rabbitmq-login.txt
    ```

### Get RabbitMQ Management UI Password

1. Store password in an environment variable

    ```terminal:execute 
    command: export RMQ_PASSWORD="$(kubectl get secret {{ ENV_RABBITMQ_SERVER_NAME }}-default-user -n {{ENV_RMQ_KUB_NAMESPACE}} -o jsonpath='{.data.password}' | base64 --decode)" 
    session: rabbitmq 
    ```

2. View password value

    ```terminal:execute 
    command: echo "password => $RMQ_PASSWORD"
    session: rabbitmq 
    ```

3.  Copy username value and paste in the file linked below

    ```editor:open-file
    file: ~/templates/data-services/rabbitmq/rabbitmq-login.txt
    ```


## RabbitMQ Management UI
[ Description of Task ]

The management UI is running on port 15672 in the rabbitmq cluster we just created. To access this UI we have to forward that port content to port 15672 on this device.

1. Access the Management UI
    ```terminal:execute 
    command: kubectl port-forward "service/{{ ENV_RABBITMQ_SERVER_NAME }}" -n {{ ENV_RMQ_KUB_NAMESPACE }} 15672 
    session: rabbitmq 
    ```
2. Open RMQ Management UI

    ```dashboard:open-dashboard 
    name: RabbitMQ 
    ```
3. Use the login information we stored in the following file to login

    ```editor:open-file
    file: ~/templates/data-services/rabbitmq/rabbitmq-login.txt
    ```

<!-- Note: You cannot use colons incode blocks. The code block are treated as yamls and they will not be interepetted correcting if ther is a stray colon.  -->