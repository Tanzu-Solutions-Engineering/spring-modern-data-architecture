
helm registry login tanzu-valkey.packages.broadcom.com \
   --username=$BROADCOM_MAVEN_USERNAME \
   --password="$BROADCOM_MAVEN_PASSWORD"


helm pull oci://tanzu-valkey.packages.broadcom.com/tanzu-valkey-helm --version 1.0.0-beta --untar --untardir /tmp


kubectl create namespace valkey-system


kubectl create secret docker-registry tanzu-image-registry --docker-server=https://tanzu-valkey.packages.broadcom.com/ --docker-username=$BROADCOM_MAVEN_USERNAME   --docker-password=$BROADCOM_MAVEN_PASSWORD --namespace valkey-system



helm install tanzu-for-valkey-on-kubernetes oci://tanzu-valkey.packages.broadcom.com/tanzu-valkey-helm --version 1.0.0-beta \
 -n valkey-system  --wait