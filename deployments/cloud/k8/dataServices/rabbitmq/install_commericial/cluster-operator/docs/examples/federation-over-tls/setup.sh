#!/bin/bash

kubectl create configmap definitions --from-file=definitions.json
kubectl apply -f certificate.yaml

