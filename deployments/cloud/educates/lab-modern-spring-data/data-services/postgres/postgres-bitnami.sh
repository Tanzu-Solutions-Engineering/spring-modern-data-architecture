helm repo add bitnami https://charts.bitnami.com/bitnami
helm install postgres bitnami/postgresql


sleep 10

kubectl wait pod -l=app.kubernetes.io/name=postgresql --for=condition=Ready --timeout=360s

# ---------------

export POSTGRES_PASSWORD=$(kubectl get secret --namespace default postgres-postgresql -o jsonpath="{.data.postgres-password}" | base64 -d)

 kubectl run postgres-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:14.4.0-debian-11-r13 --env="PGPASSWORD=$POSTGRES_PASSWORD" \
      --command -- psql --host postgres-postgresql -U postgres -d postgres -p 5432


 kubectl run postgres-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:14.4.0-debian-11-r13 --env="PGPASSWORD=$POSTGRES_PASSWORD" \
      --command -- psql --host postgres-postgresql -U postgres -d postgres -p 5432  -c "ALTER USER postgres PASSWORD 'CHANGEME'"
