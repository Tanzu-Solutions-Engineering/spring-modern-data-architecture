#!/bin/bash

export HELM_EXPERIMENTAL_OCI=1

STATUS=1
ATTEMPTS=0

until [ $STATUS -eq 0 ] || [ $ATTEMPTS -eq 3 ]; do
     sleep 5

     echo "Exe: create namespace gemfire-system" >~/.install.log
     kubectl create namespace gemfire-system >>~/.install.log 2>>~/.install.log

     echo "kubectl apply -f deployments/cloud/educates/lab-modern-spring-data/data-services/.secret">>~/.install.log
     kubectl apply -f ~/data-services/.secret  >>~/.install.log 2>>~/.install.log


     echo "Exe: create rolebinding psp-gemfire --namespace=gemfire-system --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default ">>~/.install.log
     kubectl create rolebinding psp-gemfire --namespace=gemfire-system --clusterrole=psp:vmware-system-privileged --serviceaccount=gemfire-system:default  >>~/.install.log 2>>~/.install.log

     helm uninstall gemfire-crd  --namespace gemfire-system  >>~/.install.log 2>>~/.install.log


    export HARBOR_USER=`kubectl get secret image-pull-secret -o jsonpath='{.data.*}'|  base64 --decode | jq -r '.auths."registry.tanzu.vmware.com".username'`
    export HARBOR_PASSWORD=`kubectl get secret image-pull-secret -o jsonpath='{.data.*}'|  base64 --decode | jq -r '.auths."registry.tanzu.vmware.com".password'`

     helm registry login -u $HARBOR_USER -p $HARBOR_PASSWORD registry.tanzu.vmware.com >>~/.install.log 2>>~/.install.log

    echo "helm pull oci://registry.tanzu.vmware.com/tanzu-gemfire-for-kubernetes/gemfire-crd --version 2.0.0 --destination ./" >>~/.install.log 2>>~/.install.log
     helm pull oci://registry.tanzu.vmware.com/tanzu-gemfire-for-kubernetes/gemfire-crd --version 2.0.0 --destination ./  >>~/.install.log 2>>~/.install.log


     helm pull oci://registry.tanzu.vmware.com/tanzu-gemfire-for-kubernetes/gemfire-operator --version 2.0.0 --destination ./  >>~/.install.log 2>>~/.install.log

      helm install gemfire-crd gemfire-crd-2.0.0.tgz --namespace gemfire-system --set operatorReleaseName=gemfire-operator >>~/.install.log 2>>~/.install.log
      helm install gemfire-operator gemfire-operator-2.0.0.tgz --namespace gemfire-system >>~/.install.log 2>>~/.install.log

     STATUS=$?
     ATTEMPTS=$((ATTEMPTS + 1))

      helm ls --namespace gemfire-system
      kubectl get pods -n gemfire-system
done