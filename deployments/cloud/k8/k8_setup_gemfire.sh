# Multi-Cloud Native Data with Spring and VMware Data Solutions

kubectl create namespace retail-demo
kubectl config set-context --current --namespace=retail-demo

# Analytic Cloud
kubectl create secret docker-registry tanzu-rabbitmq-registry-creds --namespace=retail-demo --docker-server=registry.tanzu.vmware.com --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl apply -f deployments/cloud/k8/dataServices/rabbitmq/vmware-rabbitmq-1-node.yml

kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=rabbitmq  --timeout=360s

kubectl apply -f deployments/cloud/k8/dataServices/rabbitmq/secret/users/rabbitmq-user.yml

kubectl apply -f deployments/cloud/k8/dataServices/rabbitmq/replication/analytics-cloud/topology/analytics-topology.yaml
kubectl apply -f deployments/cloud/k8/apps/http-amqp-source/http-amqp-source.yml

kubectl apply -f deployments/cloud/k8/dataServices/postgres/postgres.yml
kubectl wait pod -l=app=postgres --for=condition=Ready --timeout=360s


kubectl apply -f deployments/cloud/k8/apps/retail-analytics-app/retail-analytics-app-postgres.yaml


kubectl apply -f deployments/cloud/k8/dataServices/rabbitmq/replication/analytics-cloud/topology/analytics-topology.yaml

# Transactional Cloud
# Install GemFire

cd deployments/cloud/k8/dataServices/gemfire/
./gf-k8-setup.sh

kubectl apply -f deployments/cloud/k8/dataServices/gemfire/redis/gf-redis.yaml


# Transactional Cloud

kubectl apply -f deployments/cloud/k8/dataServices/rabbitmq/vmware-rabbitmq-1-node.yml


kubectl apply -f deployments/cloud/k8/apps/retail-web-app/retail-web-app-gemfire.yml
kubectl apply -f deployments/cloud/k8/apps/retail-cache-sink-app/retail-cache-sink-app-gemfire.yaml
kubectl apply -f deployments/cloud/k8/dataServices/rabbitmq/replication/web-cloud/secret/analytics-to-web-secret.yml

kubectl apply -f deployments/cloud/k8/dataServices/rabbitmq/replication/web-cloud/a
nalytics-to-web-shovel.yaml
kubectl apply -f deployments/cloud/k8/apps/retail-source-app/retail-source-app.yaml