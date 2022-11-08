#!/bin/bash

set -x #echo on

# Set GemFire Pre-Requisite

kubectl create namespace cert-manager

kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.9.1/cert-manager.yaml

kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD


# Install Postgres

#docker load -i ~/dataServices/postgres/postgres-for-kubernetes-v1.8.0/images/postgres-instance
#docker load -i ~/dataServices/postgres/postgres-for-kubernetes-v1.8.0/images/postgres-operator
docker images "postgres-*"
export HELM_EXPERIMENTAL_OCI=1
helm registry login registry.tanzu.vmware.com --username=$HARBOR_USER --password=$HARBOR_PASSWORD

kubectl create secret docker-registry regsecret \
--docker-server=https://registry.tanzu.vmware.com --docker-username=$HARBOR_USER \
--docker-password=$HARBOR_PASSWORD

helm pull oci://registry.tanzu.vmware.com/tanzu-sql-postgres/postgres-operator-chart --version v1.9.0 --untar --untardir /tmp

kubectl create namespace sql-system
helm install --wait postgres-operator /tmp/postgres-operator/ --namespace=sql-system
kubectl get crd postgresbackups.sql.tanzu.vmware.com

sleep 30
kubectl apply -f deployments/cloud/k8/dataServices/postgres/postgres.yml
sleep 40
kubectl wait pod -l=app=postgres --for=condition=Ready --timeout=360s
kubectl wait pod -l=statefulset.kubernetes.io/pod-name=postgres-0 --for=condition=Ready --timeout=360s
