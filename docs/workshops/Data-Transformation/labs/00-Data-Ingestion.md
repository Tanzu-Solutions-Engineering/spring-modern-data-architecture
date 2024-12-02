
# Start RabbitMQ

Create the docker network

```shell
docker network create tanzu
```


- Run RabbitMQ (user/bitnami)
```shell
docker run --name rabbitmq01  --network tanzu --rm -d -e RABBITMQ_MANAGEMENT_ALLOW_WEB_ACCESS=true -p 5672:5672 -p 5552:5552 -p 15672:15672  -p  1883:1883  bitnami/rabbitmq:4.0.4 
```


# Start SCDF


Download SCDF Jars

```shell
mkdir -p runtime/scdf
wget  --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-server/2.11.5/spring-cloud-dataflow-server-2.11.5.jar
wget --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-skipper-server/2.11.5/spring-cloud-skipper-server-2.11.5.jar
wget --directory-prefix=runtime/scdf https://repo.maven.apache.org/maven2/org/springframework/cloud/spring-cloud-dataflow-shell/2.11.5/spring-cloud-dataflow-shell-2.11.5.jar
```


Start Skipper
```shell
java -jar runtime/scdf/spring-cloud-skipper-server-2.11.5.jar
```


Start Data Flow Server 
```shell
export SPRING_APPLICATION_JSON='{"spring.cloud.stream.binders.rabbitBinder.environment.spring.rabbitmq.username":"user","spring.cloud.stream.binders.rabbitBinder.environment.spring.rabbitmq.password":"bitnami","spring.rabbitmq.username":"user","spring.rabbitmq.password":"bitnami","spring.cloud.dataflow.applicationProperties.stream.spring.rabbitmq.username" :"user","spring.cloud.dataflow.applicationProperties.stream.spring.rabbitmq.password" :"bitnami"}'

java -jar runtime/scdf/spring-cloud-dataflow-server-2.11.5.jar
```


Open Dashboard

```shell
open http://localhost:9393/dashboard
```


```shell
data-ingestion=http --port=9001 | log
```

```shell
curl -X POST http://localhost:9001  \
   -H 'Content-Type: application/json' \
    -d '{"login":"my_login","password":"my_password"}'
```