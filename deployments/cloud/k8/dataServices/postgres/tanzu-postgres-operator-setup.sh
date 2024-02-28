#!/bin/bash

set -x #echo on

# Set GemFire Pre-Requisite

kubectl create namespace cert-manager
kubectl create namespace sql-system

kubectl patch storageclass standard -p '{"metadata": {"annotations":{"storageclass.kubernetes.io/is-default-class":"false"}}}'
kubectl apply -f deployments/cloud/k8/dataServices/postgres/storage-class.yaml

kubectl apply -f https://raw.githubusercontent.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/4951bdd726dd09b9659a49dcadfb6b0cb3f7863f/deployments/cloud/k8/dataServices/postgres/storage-class.yaml

kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.9.1/cert-manager.yaml

sleep 5
kubectl wait pod -l=app=cert-manager --for=condition=Ready --timeout=360s --namespace=cert-manager
kubectl wait pod -l=app=cainjector --for=condition=Ready --timeout=360s --namespace=cert-manager
kubectl wait pod -l=app=webhook --for=condition=Ready --timeout=360s --namespace=cert-manager


kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$PIVOTAL_MAVEN_USERNAME --docker-password=$PIVOTAL_MAVEN_PASSWORD


# Install Postgres

#docker load -i ~/dataServices/postgres/postgres-for-kubernetes-v1.8.0/images/postgres-instance
#docker load -i ~/dataServices/postgres/postgres-for-kubernetes-v1.8.0/images/postgres-operator
#docker images "postgres-*"
export HELM_EXPERIMENTAL_OCI=1
helm registry login registry.tanzu.vmware.com --username=$PIVOTAL_MAVEN_USERNAME --password=$PIVOTAL_MAVEN_PASSWORD

#helm registry login registry.tanzu.vmware.com --username=$PIVOTAL_MAVEN_USERNAME --password=$PIVOTAL_MAVEN_PASSWORD -n sql-system


kubectl create secret docker-registry regsecret \
--docker-server=https://registry.tanzu.vmware.com --docker-username=$PIVOTAL_MAVEN_USERNAME \
--docker-password=$PIVOTAL_MAVEN_PASSWORD

kubectl create secret docker-registry regsecret \
--docker-server=https://registry.tanzu.vmware.com --docker-username=$PIVOTAL_MAVEN_USERNAME \
--docker-password=$PIVOTAL_MAVEN_PASSWORD -n sql-system

rm -rf /tmp/vmware-sql-postgres-operator
helm pull oci://registry.tanzu.vmware.com/tanzu-sql-postgres/vmware-sql-postgres-operator --version v2.3.0 --untar --untardir /tmp



#helm install my-postgres-operator /tmp/postgres-operator/  --namespace=sql-system --wait
helm install --wait postgres-operator /tmp/vmware-sql-postgres-operator/ --namespace=sql-system
kubectl get crd postgresbackups.sql.tanzu.vmware.com

#sleep 30
#kubectl apply -f deployments/cloud/k8/dataServices/postgres/postgres.yml
#sleep 40
#kubectl wait pod -l=app=postgres --for=condition=Ready --timeout=360s
#kubectl wait pod -l=statefulset.kubernetes.io/pod-name=postgres-0 --for=condition=Ready --timeout=360s
