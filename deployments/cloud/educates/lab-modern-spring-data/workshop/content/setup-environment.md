## Resource Available in this workshop

### Terminals
* Gemfire Terminal

    ```terminal:execute 
    command: echo "You will run all your Gemfire commands here! "
    session: gemfire 
    ```

* RabbitMQ Terminal

    ```terminal:execute 
    command: echo "You will run all your RabbitMQ commands here! "
    session: rabbitmq 
    ```
* Postgres Terminal

    ```terminal:execute
    command: echo "You will run all your Terminal commands here! "
    session: postgres 
    ```

* Retail Web App Terminal

    ```terminal:execute 
    command: echo "You will run all your Retail Web App commands here! "
    session: retailapp 
    ```

### VS Code Editor:

```dashboard:open-dashboard
name: Editor
```


### Kubernetes Cluster Operators
[VMware Data Solutions](https://tanzu.vmware.com/data-solutions) such as 
[GemFire](https://tanzu.vmware.com/gemfire), [SQL (Postgres)](https://tanzu.vmware.com/sql) and [RabbitMQ](https://tanzu.vmware.com/rabbitmq) will be used for this workshops.

The RabbitMQ and Postgres data solutions will be installed using the Kubernetes [Operator Pattern](https://kubernetes.io/docs/concepts/extend-kubernetes/operator/).

The Operators manage the installation and health of the running data solutions.
The operator have embedded logic to manage the data solutions based on best practices.


The following Operators are currently installed

* [RabbitMQ Cluster Operator](https://www.rabbitmq.com/kubernetes/operator/operator-overview.html)

    ```terminal:execute
    command: k get pods -n rabbitmq-system
    session: rabbitmq
    ```

* [Postgres](https://docs.vmware.com/en/VMware-Tanzu-SQL-with-Postgres-for-Kubernetes/index.html)

    ```terminal:execute
    command: k get pods -n sql-system
    session: postgres
    ```

