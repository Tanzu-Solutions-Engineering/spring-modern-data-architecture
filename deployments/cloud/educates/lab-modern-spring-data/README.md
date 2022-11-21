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
---------------------

# Creating a Workshop


Download templates

imgpkg pull -i ghcr.io/vmware-tanzu-labs/educates-workshop-templates:latest -o educates-workshop-templates

Limit to 25 characters (start with lab)

```shell
educates-workshop-templates/create-workshop.sh lab-modern-spring-data --output . --overlay virtual-cluster
```
