# Step VMware Container Registry Credentials


Replace CHANGEME with registered username/email at [network.tanzu.vmware.com](https://network.tanzu.vmware.com/)

```copy
export HARBOR_USER=CHANGEME
```

Replace CHANGEME with password of registered username/email at [network.tanzu.vmware.com](https://network.tanzu.vmware.com/)

```copy
export HARBOR_PASSWORD=CHANGEME
```


Create Secret in current namespace

```execute
kubectl create secret docker-registry image-pull-secret --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
```

# Setup GemFire Operator

```execute
kubectl create namespace gemfire-system
```

Create pull secret in gemfire-system namespace

```execute
kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD
```

Install the GemFire Operator
```execute
# Setup GemFire Operator
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

```


Check GemFire Operator running


```execute
k get pods -n gemfire-system
```
