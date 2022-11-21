#!/bin/bash

# Wait for CRDs for kapp-controller to have been created.

STATUS=1
ATTEMPTS=0
COMMAND="kubectl get crd/packagerepositories.packaging.carvel.dev"

until [ $STATUS -eq 0 ] || $COMMAND || [ $ATTEMPTS -eq 12 ]; do
    sleep 5
    $COMMAND
    STATUS=$?
    ATTEMPTS=$((ATTEMPTS + 1))
done

# Now wait for deployment of kapp-controller.

STATUS=1
ATTEMPTS=0
COMMAND="kubectl rollout status deployment/kapp-controller -n kapp-controller"

until [ $STATUS -eq 0 ] || $COMMAND || [ $ATTEMPTS -eq 12 ]; do
    sleep 5
    $COMMAND
    STATUS=$?
    ATTEMPTS=$((ATTEMPTS + 1))
done


# install CRD
kubectl create namespace cert-manager
helm repo add jetstack https://charts.jetstack.io
helm repo update
kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.9.0/cert-manager.yaml