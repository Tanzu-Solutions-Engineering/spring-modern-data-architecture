[ Description of Activity ]

# GemFire for Redis Cluster
[ Description of Task ]

1. View GemFire for Redis Cluster Definition

    ```editor:open-file
    file: ~/data-services/gemfire/gemfire-redis.yml
    ```
    
2. Create GemFire for Redis Cluster

    ```terminal:execute
    command: k apply -f data-services/gemfire/gemfire-redis.yml
    session: gemfire
    ```

# Wait for 1 locator and 1 server to be created
[ Description of Task ]

1. Create a Gefmire Redis Locator

    ```terminal:execute
    command: kubectl wait pod -l=gemfire.vmware.com/app=gf-redis-locator --for=condition=Ready --timeout=720s; sleep 10s;
    session: gemfire
    ```

2. Create a Gemfire Redis Server

    ```terminal:execute
    command: kubectl wait pod -l=statefulset.kubernetes.io/pod-name=gf-redis-server-0  --for=condition=Ready --timeout=720s
    session: gemfire
    ```

# Deploying Spring Boot Web Application
The demo source code contains a [retail-web-app](https://github.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/tree/main/applications/retail-web-app)
Spring Boot application. 

The docker image of this application
Was build using the following instructions.

```shell
mvn install
cd applications/retail-web-app
mvn spring-boot:build-image
```

The docker image has been published to Docker Hub.

1. View Retail Web App Defintion

    ```editor:open-file
    file: ~/apps/retail-web-app.yml
    ```

2. Create retail web application

    ```terminal:execute
    command: k apply -f apps/retail-web-app.yml
    session: retailapp
    ```

3. Wait for application

    ```terminal:execute
    command: kubectl wait pod -l=name=retail-web-app --for=condition=Ready --timeout=60s
    session: retailapp
    ``` 

4. Get Ingress

    ```terminal:execute
    command: k get ingress
    session: retailapp
    ```

  workshop_name: {{ workshop_name }}

  session_namespace: {{ session_namespace }}

  workshop_namespace: {{ workshop_namespace }}

5. Make Ingress accessible on the web

    ```terminal:execute
    command: k port-forward pod/retail-web-app-68f94cddf-pfd2q 8080
    session: retailapp
    ```

6. Open browser to address

    ```dashboard:open-dashboard
    name: Retail Web App
    url: http://retail-web-app-{{ session_namespace }}.{{ ingress_domain }}
    ```

## Real-time data to Web with Redis Pub/Sub
GemFire for Redis Apps can be used as a light-weight messaging engine to delivery 
realtime low latency message using in-memory data processes.

See [PromoteController](https://github.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/blob/main/applications/retail-web-app/src/main/java/com/vmware/retail/controller/PromoteController.java)

```java
@RequestMapping("promotions")
public record PromoteController(PromotionRepository repository, RedisTemplate<String,Promotion> redisTemplate)
{
    //...
    @PostMapping("promotion/publish")
    public void publishPromotion(@RequestBody Promotion promotion)
    {
        redisTemplate.convertAndSend(channel,promotion);
    }
```

Post a promotion directly to the web application.

```execute
curl -X 'POST' \
  'http://retail-web-app-{{ session_namespace }}.{{ ingress_domain }}/promotions/promotion/publish' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "nyla",
  "marketingMessage": "string",
  "products": [
    {
      "id": "1",
      "name": "Peanut Butter"
    }
  ]
}'
```

# Spring Data Redis Repository
Spring Data  makes it very easy to perform Create, Read, Update and Delete (CRUD) operations,
without having to know the low level details of the data solutions like Redis or GemFire for Redis
applications.

All you need is a Repository interface. Spring Data implements
the low level data access details using the data solutions API(s).

See [CustomerFavoriteRepository](https://github.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/blob/main/components/retail-repositories-caching/src/main/java/com/vmware/retail/repository/CustomerFavoriteRepository.java)

```java
@Repository
public interface CustomerFavoriteRepository extends KeyValueRepository<CustomerFavorites,String>
{
}
```
# Web Reactive with Spring WebFlux
JavaScript Server Side Events can be keep web browser content such as customer favorites
updated using Spring Reactive Web Controllers.

See [ReadCustomerFavoritesController](https://github.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/blob/main/applications/retail-web-app/src/main/java/com/vmware/retail/controller/ReadCustomerFavoritesController.java)

```java
@RestController
@RequestMapping(value = "customer/favorites", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public class ReadCustomerFavoritesController
{
    @GetMapping("favorite/{id}")
    public Flux<ServerSentEvent<CustomerFavorites>> streamEvents(@PathVariable String id) {
        logger.info("id: {}",id);

        Scheduler scheduler = Schedulers.newParallel(5,factory);
        return Flux.interval(Duration.ofSeconds(refreshRateSecs),scheduler)
                .map(sequence -> ServerSentEvent.<CustomerFavorites> builder()
                        .data(repository.findById(id).orElse(null))
                        .build());
    }
```

# Low Latency Cache Update with Redis
Using a CQRS pattern, separate Reads from Write controllers.

The [SaveCustomerFavoritesController](https://github.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/blob/main/applications/retail-web-app/src/main/java/com/vmware/retail/controller/SaveCustomerFavoritesController.java) provides write HTTP endpoint to save to the repository

```java
@RestController
@RequestMapping("customer/favorites")
public class SaveCustomerFavoritesController
{
    //...
    @PostMapping("favorite")
    public void saveCustomerFavorites(@RequestBody CustomerFavorites customerFavorites)
    {
        repository.save(customerFavorites);
    }
```

Use the controller to save customer favorite data in Redis

```execute
curl -X 'POST' \
  'http://retail-web-app-{{ session_namespace }}.{{ ingress_domain }}/customer/favorites/favorite' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "id": "nyla",
  "favorites": [
    {
      "product": {
        "id": "1",
        "name": "Milk"
      },
      "quantity": 1
    }
  ]
}'
```

