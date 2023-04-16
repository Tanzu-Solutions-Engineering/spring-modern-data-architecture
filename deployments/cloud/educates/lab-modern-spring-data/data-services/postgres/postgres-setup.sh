#!/bin/bash

set -x #echo on

# Set GemFire Pre-Requisite

kubectl create namespace cert-manager
kubectl create namespace gemfire-system

helm repo add jetstack https://charts.jetstack.io

helm repo update

helm install cert-manager jetstack/cert-manager --namespace cert-manager  --version v1.0.2 --set installCRDs=true

kubectl create secret docker-registry image-pull-secret --namespace=gemfire-system --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD

kubectl create secret docker-registry image-pull-secret --docker-server=registry.pivotal.io --docker-username=$HARBOR_USER --docker-password=$HARBOR_PASSWORD


# Install Postgres

docker load -i ~/dataServices/postgres/postgres-for-kubernetes-v1.2.0/images/postgres-instance
docker load -i ~/dataServices/postgres/postgres-for-kubernetes-v1.2.0/images/postgres-operator
docker images "postgres-*"
export HELM_EXPERIMENTAL_OCI=1
helm registry login registry.pivotal.io --username=$HARBOR_USER --password=$HARBOR_PASSWORD

helm chart pull registry.pivotal.io/tanzu-sql-postgres/postgres-operator-chart:v1.2.0
helm chart export registry.pivotal.io/tanzu-sql-postgres/postgres-operator-chart:v1.2.0  --destination=/tmp/

kubectl create secret docker-registry regsecret \
--docker-server=https://registry.pivotal.io --docker-username=$HARBOR_USER \
--docker-password=$HARBOR_PASSWORD

helm install --wait postgres-operator /tmp/postgres-operator/

sleep 30
kubectl apply -f cloud/k8/data-services/postgres
sleep 40
kubectl wait pod -l=app=postgres --for=condition=Ready --timeout=360s
kubectl wait pod -l=statefulset.kubernetes.io/pod-name=postgres-0 --for=condition=Ready --timeout=360s


# ---------------

kubectl exec -it postgres-0 -- psql -c "ALTER USER postgres PASSWORD 'CHANGEME'"

kubectl exec -it postgres-0 -- psql -c "CREATE TABLE bank_atms ( atm_id varchar(50) NOT NULL, bank_id varchar(50) NULL, atm_name varchar(50) NULL, address_line_1 varchar(50) NULL, address_line_2 varchar(50) NULL, address_line_3 varchar(50) NULL, address_city varchar(50) NULL, address_county varchar(50) NULL, address_state varchar(50) NULL, address_postcode varchar(50) NULL, address_country_code varchar(50) NULL, meta_license_id varchar(50) NULL, meta_license_name varchar(50) NULL, monday_opening_time varchar(50) NULL, monday_closing_time varchar(50) NULL, tuesday_opening_time varchar(50) NULL, tuesday_closing_time varchar(50) NULL, wednesday_opening_time varchar(50) NULL, wednesday_closing_time varchar(50) NULL, thursday_opening_time varchar(50) NULL, thursday_closing_time varchar(50) NULL, friday_opening_time varchar(50) NULL, friday_closing_time varchar(50) NULL, saturday_opening_time varchar(50) NULL, saturday_closing_time varchar(50) NULL, sunday_opening_time varchar(50) NULL, sunday_closing_time varchar(50) NULL, is_accessible varchar(50) NULL, located_at varchar(50) NULL, more_info varchar(50) NULL, has_deposit_capability varchar(50) NULL, update_ts timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP );"


kubectl exec -it postgres-0 -- psql -c "CREATE TABLE BANK_ACCOUNTS ( ACCT_ID  TEXT  NOT NULL, BANK_ID  TEXT  NOT NULL, ACCT_LABEL  TEXT NOT NULL, ACCT_NUMBER TEXT NOT NULL, ACCT_PRODUCT_CD TEXT NOT NULL, ACCT_ROUTINGS TEXT, ACCT_VIEWS_BASIC  TEXT, ACCT_BALANCE NUMERIC(30,2) DEFAULT 0, UPDATE_TS TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(ACCT_ID, BANK_ID));"

kubectl exec -it postgres-0 -- psql -c "CREATE TABLE banks (bank_id varchar(50) PRIMARY KEY, short_name varchar(50) NOT NULL,full_name varchar(50) NOT NULL,logo varchar(50) NULL, website varchar(50) NOT NULL, bank_routings varchar(50) NULL,update_ts timestamp(3) NULL DEFAULT CURRENT_TIMESTAMP);"