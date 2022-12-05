#!/bin/bash

kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create namespace gemfire-system

kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create rolebinding psp-gemfire --namespace=gemfire-system --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default


# Install the GemFire Operator
curl -o ./gemfire-crd-2.1.0.tgz https://spring-modern-data-architecture-files.s3.us-west-1.amazonaws.com/gemfire-crd-2.1.0.tgz
curl -o ./gemfire-operator-2.1.0.tgz https://spring-modern-data-architecture-files.s3.us-west-1.amazonaws.com/gemfire-operator-2.1.0.tgz

STATUS=1
ATTEMPTS=0
COMMAND="helm install gemfire-crd  ./gemfire-crd-2.1.0.tgz --set operatorReleaseName=gemfire-operator --namespace gemfire-system"

until [ $STATUS -eq 0 ] || $COMMAND || [ $ATTEMPTS -eq 12 ]; do
    sleep 5
    $COMMAND
    STATUS=$?
    ATTEMPTS=$((ATTEMPTS + 1))
done


STATUS=1
ATTEMPTS=0
COMMAND="helm install gemfire-operator  ./gemfire-operator-2.1.0.tgz --namespace gemfire-system"

until [ $STATUS -eq 0 ] || $COMMAND || [ $ATTEMPTS -eq 12 ]; do
    sleep 5
    $COMMAND
    STATUS=$?
    ATTEMPTS=$((ATTEMPTS + 1))
done


kubectl wait pod -l=app.kubernetes.io/instance=gemfire-operator --for=condition=Ready --timeout=260s  -n gemfire-system
