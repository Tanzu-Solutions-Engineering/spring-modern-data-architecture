# Step VMware Container Registry Credentials


[VMware Data Solutions](https://tanzu.vmware.com/data-solutions) such as 
[GemFire](https://tanzu.vmware.com/gemfire), [SQL (Postgres)](https://tanzu.vmware.com/sql) and [RabbitMQ](https://tanzu.vmware.com/rabbitmq) will be used for this workshops.

Each of these data solutions have will be installed using the Kubernetes [Operator Pattern](https://kubernetes.io/docs/concepts/extend-kubernetes/operator/).

# Installed Operators

The following Operators are currently installed

- [GemFire](https://docs.vmware.com/en/VMware-Tanzu-GemFire-for-Kubernetes/2.0/tgf-k8s/GUID-install.html)
```execute
k get pods -n gemfire-system
```
- [RabbitMQ](https://www.rabbitmq.com/kubernetes/operator/operator-overview.html)

```execute
k get pods -n rabbitmq-system
```

- [Postgres](https://docs.vmware.com/en/VMware-Tanzu-SQL-with-Postgres-for-Kubernetes/index.html)
```execute
k get pods -n sql-system
```

Each Data Solution Operators manages the installation and health of the running data solutions.
The operator have embedded logic to manage the data solutions based on best practices.
