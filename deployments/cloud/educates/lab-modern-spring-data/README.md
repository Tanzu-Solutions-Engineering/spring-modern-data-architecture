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