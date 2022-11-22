# Install GemFire for Redis Cluster

View GemFire for Redis Cluster Definition
```editor:open-file
file: ~/data-services/gemfire-redis.yml
```


Create GemFire for Redis Cluster

```execute
k apply -f data-services/gemfire.yml
```

Wait for 1 locator and 1 server to be created


Create retail web application

```execute
k apply -f apps/retail-web-app.yml
```

```execute
helm repo update
```

```execute
helm repo update
```

Download gemfire-crd

Download gemfire-operator


```


```shell

sleep 5s
kubectl wait pod -l=app.kubernetes.io/component=gemfire-controller-manager --for=condition=Ready --timeout=160s --namespace=gemfire-system

```

#### Standard code block

```
echo "standard code block"
```

#### Click text to execute

```execute
echo "execute in terminal 1"
```

#### Click text to execute (with target)

```execute-1
echo "execute in terminal 1"
```

```execute-2
echo "execute in terminal 2"
```

```execute-all
echo "execute in all terminals"
```

#### Click text to copy

```copy
echo "copy text to buffer"
```

#### Click text to copy (and edit)

```copy-and-edit
echo "copy text to buffer"
```

#### Interrupt command

```execute
sleep 3600
```

```execute
<ctrl-c>
```

#### Variable interpolation

workshop_name: {{ workshop_name }}

session_namespace: {{ session_namespace }}

workshop_namespace: {{ workshop_namespace }}

training_portal: {{ training_portal }}

ingress_domain: {{ ingress_domain }}

ingress_protocol: {{ ingress_protocol }}

#### Web site links

[External](https://github.com/eduk8s)
