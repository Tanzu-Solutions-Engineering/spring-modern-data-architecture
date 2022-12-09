Starter Workshop
================

Starter workshop for Educates

# Setup


Create

kubectl get trainingportals


# Cleanup


```shell
kubectl delete workshop,trainingportal lab-modern-spring-data

```


Update image in kind


```shell
kind load docker-image localhost:5001/lab-modern-spring-data-image -n educates
```

---------------------

# Creating a Workshop


Download templates

imgpkg pull -i ghcr.io/vmware-tanzu-labs/educates-workshop-templates:latest -o educates-workshop-templates

Limit to 25 characters (start with lab)

```shell
educates-workshop-templates/create-workshop.sh lab-modern-spring-data --output . --overlay virtual-cluster
```
-------------------


Seal secret

brew install kubeseal

helm repo add sealed-secrets https://bitnami-labs.github.io/sealed-secrets

k apply -f https://github.com/bitnami-labs/sealed-secrets/releases/download/v0.19.2/controller.yaml

kubectl create secret generic --dry-run=client -o json mysecret  --from-literal=password=supersekret |  kubeseal > mysealedsecret.json

# Eventually upload mysealedsecret to cluster:
kubectl create -f mysealedsecret.json

# The original secret now exists in the cluster, like magic!
kubectl get secret mysecret


kind delete cluster -n educates



---------------------



workshop_name: {{ workshop_name }}

session_namespace: {{ session_namespace }}

workshop_namespace: {{ workshop_namespace }}


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