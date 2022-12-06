# GemFire for Redis Cluster

View GemFire for Redis Cluster Definition
```editor:open-file
file: ~/data-services/gemfire-redis.yml
```


Create GemFire for Redis Cluster

```execute
k apply -f data-services/gemfire-redis.yml
```

Wait for 1 locator and 1 server to be created

```execute
kubectl wait pod -l=gemfire.vmware.com/app=gf-redis-locator --for=condition=Ready --timeout=720s
sleep 1s
kubectl wait pod -l=statefulset.kubernetes.io/pod-name=gf-redis-server-0  --for=condition=Ready --timeout=720s
```
# Deploying Spring Boot Web Application

Create retail web application

```execute
k apply -f apps/retail-web-app.yml
```

Wait for application

```execute
kubectl wait pod -l=name=retail-web-app --for=condition=Ready --timeout=60s
```

Get Ingress

```execute
k get ingress
```

Open browser to address
```dashboard:RetailApp
url: http://retail-web-app-$(session_namespace).$(ingress_domain)
```

# Real-time data to Web with Redis Pub/Sub

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
  'http://retail-web-app-$(session_namespace).$(ingress_domain)/promotions/promotion/publish' \
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
  'http://retail-web-app-$(session_namespace).$(ingress_domain)/promotions/promotion/publish' \
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

workshop_name: {{ workshop_name }}

session_namespace: {{ session_namespace }}

workshop_namespace: {{ workshop_namespace }}




#### Standard code block

```
echo "standard code block"
```

#### Click text to execute

```execute
echo "execute in terminal 1"
```

#### Click text to execute (with target)

```execute-1
echo "execute in terminal 1"
```

```execute-2
echo "execute in terminal 2"
```

```execute-all
echo "execute in all terminals"
```

#### Click text to copy

```copy
echo "copy text to buffer"
```

#### Click text to copy (and edit)

```copy-and-edit
echo "copy text to buffer"
```

#### Interrupt command

```execute
sleep 3600
```

```execute
<ctrl-c>
```

#### Variable interpolation

workshop_name: {{ workshop_name }}

session_namespace: {{ session_namespace }}

workshop_namespace: {{ workshop_namespace }}

training_portal: {{ training_portal }}

ingress_domain: {{ ingress_domain }}

ingress_protocol: {{ ingress_protocol }}

#### Web site links

[External](https://github.com/eduk8s)
