k apply -f ~/data-services/.secret

export HARBOR_USER=`kubectl get secret image-pull-secret -o jsonpath='{.data.*}'|  base64 --decode | jq -r '.auths."registry.tanzu.vmware.com".username'`
export HARBOR_PASSWORD=`kubectl get secret image-pull-secret -o jsonpath='{.data.*}'|  base64 --decode | jq -r '.auths."registry.tanzu.vmware.com".password'`


helm registry login registry.tanzu.vmware.com \
       --username=$HARBOR_USER \
       --password=$HARBOR_PASSWORD

helm pull oci://registry.tanzu.vmware.com/tanzu-sql-postgres/postgres-operator-chart --version v1.9.1 --untar --untardir /tmp

kubectl create secret docker-registry regsecret \
    --docker-server=https://registry.tanzu.vmware.com/ \
    --docker-username=$HARBOR_USER \
    --docker-password=$HARBOR_PASSWORD

k create namespace sql-system

kubectl create secret docker-registry regsecret  -n  sql-system \
    --docker-server=https://registry.tanzu.vmware.com/ \
    --docker-username=$HARBOR_USER \
    --docker-password=$HARBOR_PASSWORD

helm install postgres-operator /tmp/postgres-operator/ --wait --namespace  sql-system