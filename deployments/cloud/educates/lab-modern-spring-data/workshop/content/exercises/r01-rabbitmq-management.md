# RabbitMQ Management UI
[ Description of Activity ]

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
export RMQ_USERNAME="$(kubectl get secret {{ ENV_RABBITMQ_SERVER_NAME }}-default-user -o jsonpath='{.data.username}' | base64 --decode)"
echo "username: $RMQ_USERNAME"
session:rabbitmq
```

**Password:** 
```terminal:execute 
export RMQ_PASSWORD="$(kubectl get secret {{ ENV_RABBITMQ_SERVER_NAME }}-default-user -o jsonpath='{.data.password}' | base64 --decode)"
echo "password: $RMQ_PASSWORD"
session:rabbitmq
```

### Open RMQ Management UI
[ Description of Task ]

```dashboard:open-dashboard
name: RabbitMQ
```