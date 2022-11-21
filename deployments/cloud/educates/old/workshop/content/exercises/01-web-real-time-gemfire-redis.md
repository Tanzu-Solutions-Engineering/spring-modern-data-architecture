This is an example page for exercises to be done for the workshop. You would remove this page, replace it with your own and then adjust the `workshop.yaml` and `modules.yaml` file to list your pages instead.

In this example the pages which make up the core of the workshop content are placed in a sub directory. This is only done as a suggestion. You can place all pages at the same directory level if you wish.

Included below are some tests and examples of page formatting using Markdown.

# Install GemFire


```shell script
curl -o ./gemfire-operator-2.1.0.tgz https://spring-modern-data-architecture-files.s3.us-west-1.amazonaws.com/gemfire-operator-2.1.0.tgz
curl -o ./gemfire-crd-2.1.0.tgz https://spring-modern-data-architecture-files.s3.us-west-1.amazonaws.com/gemfire-crd-2.1.0.tgz

```


```shell
helm install gemfire-crd  ./gemfire-crd-2.1.0.tgz --set operatorReleaseName=gemfire-operator --namespace gemfire-system 
helm install gemfire-operator  ~/dataServices/gemfire/gemfire-operator-2.0.0.tgz --namespace gemfire-system

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
