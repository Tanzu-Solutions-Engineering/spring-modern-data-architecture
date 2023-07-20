#!/bin/bash

set -x #echo on

# Set GemFire Pre-Requisite

kubectl create namespace cert-manager

helm repo add jetstack https://charts.jetstack.io

helm repo update


kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.12.0/cert-manager.yaml
kubectl get pods --namespace cert-manager

kubectl create namespace gemfire-system


kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create rolebinding psp-gemfire --namespace=gemfire-system --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default

# Install the GemFire Operator
helm install gemfire-crd  ~/dataServices/gemfire/gemfire-crd-2.2.0.tgz --namespace gemfire-system --set operatorReleaseName=gemfire-operator
helm install gemfire-operator  ~/dataServices/gemfire/gemfire-operator-2.2.0.tgz --namespace gemfire-system

sleep 5s
kubectl wait pod -l=app.kubernetes.io/component=gemfire-controller-manager --for=condition=Ready --timeout=160s --namespace=gemfire-system


kubectl get pods --namespace gemfire-system

#kubectl apply -f deployments/cloud/k8/data-services/gemfire/gemfire.yml

sleep 10s
#kubectl wait pod -l=app=gemfire1-server --for=condition=Ready --timeout=160s
