#!/bin/bash

set -eo pipefail

# Parameters with default values (can override):
tanzurmqversion=1.4.1
serviceaccount=rabbitmq
namespace="rabbitmq-system"
replicas=3
prometheusrepourl="https://github.com/rabbitmq/cluster-operator.git"
prometheusoperatorversion=v2.2.0
requesttimeout=100s
vmwareuser=$HARBOR_USER
vmwarepassword=$HARBOR_PASSWORD
certmanagervsersion=1.11.0
kubectl=kubectl
maxskew=1
cluster_partition_handling=pause_minority
vm_memory_high_watermark_paging_ratio=0.99
disk_free_limit_relative=1.5
collect_statistics_interval=10000
cpu=2
memory=2Gi
antiaffinity=0 # Set to 1 in Production (pass parameter)!
storage="1Gi" # Override in Production (pass parameter)!
storageclassname="" # Override in Production (pass parameter)!

# Override parameters (if specified) e.g. --tanzurmqversion 1.2.2
while [ $# -gt 0 ]; do

   if [[ $1 == *"--"* ]]; then
        param="${1/--/}"
        echo "param: $param"
        declare $param="$2"
        # echo $1 $2 // Optional to see the parameter:value result
   fi

  shift
done

if [ -z $vmwareuser ]
then
     echo "vmwareuser not set"
     exit 1
fi

if [ -z $vmwarepassword ]
then
     echo "vmwarepassword not set"
     exit 1
fi

if [ -z $storageclassname ]; then persistent=0; else persistent=1; fi

echo "namespace: $namespace"
echo "tanzurmqversion: $tanzurmqversion"
echo "serviceaccount: $serviceaccount"
echo "replicas: $replicas"
echo "prometheusrepourl: $prometheusrepourl"
echo "prometheusoperatorversion: $prometheusoperatorversion"
echo "requesttimeout: $requesttimeout"
echo "certmanagervsersion: $certmanagervsersion"
echo "kubectl: $kubectl"

case $kubectl in
"oc") openshift=1 ;;
*) openshift=0 ;;
esac

echo "CREATE NAMESPACE $namespace if does not exist..."
$kubectl create namespace $namespace --dry-run=client -o yaml | $kubectl apply -f-

echo "CREATE SERVICEACCOUNT $serviceaccount if does not exist..."
$kubectl create serviceaccount $serviceaccount -n $namespace --dry-run=client -o yaml | $kubectl apply -f-

echo "CREATING CLUSTER ROLE"
$kubectl apply -f clusterrole.yml -n $namespace --request-timeout=$requesttimeout

echo "CREATING the CLUSTER rmq ROLE BINDING if does not exist..."
$kubectl create clusterrolebinding rmq --clusterrole tanzu-rabbitmq-crd-install --serviceaccount $namespace:$serviceaccount --request-timeout=$requesttimeout --dry-run=client -o yaml | $kubectl apply -f-

if command -v shasum &> /dev/null
then
     if command -v wget &> /dev/null
     then
          echo "INSTALLING CARVEL USING wget"
          wget -O- https://carvel.dev/install.sh | bash
     elif command -v curl &> /dev/null
     then
          echo "INSTALLING CARVEL USING curl"
          curl -L https://carvel.dev/install.sh | bash
     else
          echo "Error: neither wget nor curl detected"
          exit 1
     fi
else
     echo "WARNING: shasum IS MISSING !"
     chmod +x install_carvel.sh
     ./install_carvel.sh
fi

echo "INSTALLING KAPP-CONTROLLER"
$kubectl apply -f https://github.com/vmware-tanzu/carvel-kapp-controller/releases/latest/download/release.yml --request-timeout=$requesttimeout

echo "INSTALLING SECRETGEN-CONTROLLER"
$kubectl apply -f https://github.com/vmware-tanzu/carvel-secretgen-controller/releases/latest/download/release.yml --request-timeout=$requesttimeout

echo "INSTALLING CERT-MANAGER" # @Param: version
$kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v$certmanagervsersion/cert-manager.yaml --request-timeout=$requesttimeout


echo "CREATING VMWARE CONTAINER REGISTRY SECRET"
export RMQ_docker__username="$vmwareuser"
export RMQ_docker__password="$vmwarepassword"
export RMQ_docker__server="registry.tanzu.vmware.com"
export RMQ_rabbitmq__namespace="$namespace"
ytt -f secret.yml --data-values-env RMQ | $kubectl apply -f-

echo "DEPLOYING REPOSITORY..."
export RMQ_rabbitmq__image="registry.tanzu.vmware.com/p-rabbitmq-for-kubernetes/tanzu-rabbitmq-package-repo:$tanzurmqversion"
ytt -f repo.yml --data-values-env RMQ | kapp deploy --debug -a tanzu-rabbitmq-repo -y -n $namespace -f-

echo "DEPLOYING PACKAGE INSTALL..."
export RMQ_rabbitmq__version="$tanzurmqversion"
export RMQ_rabbitmq__serviceaccount="$serviceaccount"
ytt -f packageInstall.yml --data-values-env RMQ | kapp deploy --debug -a tanzu-rabbitmq  -y -n $namespace -f-

echo "INSTALLING HELM..."
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3
chmod +x get_helm.sh
./get_helm.sh

# TODO: fix Observability for Openshift
if [ ! -d "cluster-operator" ]
then
     if [[ $openshift -eq 1 ]]
     then
          echo "OPENSHIFT DETECTED, PLEASE INSTALL OBSERVABILITY TOOLS MANUALLY..."
     else
          echo "INSTALLING PROMETHEUS OPERATOR FROM $prometheusrepourl"
          git clone $prometheusrepourl
          cd cluster-operator/observability/
          git checkout $prometheusoperatorversion
          chmod +x quickstart.sh
          ./quickstart.sh
          cd ../../
     fi
else
     echo "Directory cluster-operator exists, skipping..."
fi

kubectl apply -f /Users/Projects/VMware/Tanzu/TanzuData/TanzuRabbitMQ/dev/tanzu-rabbitmq-event-streaming-showcase/cloud/k8/data-services/rabbitmq/secret/secrets.yml

echo "INSTALLING CLUSTERS MONITOR..."
$kubectl apply --filename https://raw.githubusercontent.com/rabbitmq/cluster-operator/main/observability/prometheus/monitors/rabbitmq-servicemonitor.yml
echo "INSTALLING OPERATORS MONITOR..."
$kubectl apply --filename https://raw.githubusercontent.com/rabbitmq/cluster-operator/main/observability/prometheus/monitors/rabbitmq-cluster-operator-podmonitor.yml
$kubectl apply -f /Users/Projects/VMware/Tanzu/TanzuData/TanzuRabbitMQ/dev/tanzu-rabbitmq-event-streaming-showcase/deployment/cloud/k8/data-services/rabbitmq/secret/secrets.yml
