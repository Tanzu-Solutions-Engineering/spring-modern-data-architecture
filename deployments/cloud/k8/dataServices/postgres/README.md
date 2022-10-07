
Get A view of the Postgres version that can be deployed

```shell
kubectl get postgresversion
```


Deploy

```shell
k apply -f deployments/cloud/k8/dataServices/postgres/postgres-sample.yml
```

Access Database

```shell
kubectl exec -it postgres-sample-0 -- psql
```

Expose connection thru services

```shell
k get services
```

Secret connection details 
```shell
k get secrets postgres-sample-db-secret -o yaml
```



Create table

```shell
CREATE TABLE accounts (
	user_id serial PRIMARY KEY,
	username VARCHAR ( 50 ) UNIQUE NOT NULL,
	password VARCHAR ( 50 ) NOT NULL,
	email VARCHAR ( 255 ) UNIQUE NOT NULL,
	created_on TIMESTAMP NOT NULL,
        last_login TIMESTAMP 
);
```

List tables

```
\dt
```


Create an HA Cluster

Contor?? secret management 

```shell
k apply -f deployments/cloud/k8/dataServices/postgres/postgres-ha.yml
```


```shell
kubectl exec -it postgres-sample-0 -- bash
```