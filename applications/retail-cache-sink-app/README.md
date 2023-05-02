
## RabbitMQ Testing

### retail.customer.favorites
Exchange  retail.customer.favorites

```json
{
  "id" : "nyla",
  "favorites" : [
    { 
      "quantity" :  1,
      "product" :  
        { 
          "id":  "1",
          "name" : "Hello"
        }
    }
  ]
}
```

### retail.customer.promotions


Exchange: retail.customer.promotions

```json
{
  "id" : "nyla",
  "marketingMessage" : "Testing",
  "products" : [
    {
          "id":  "1",
          "name" : "Product"
    }
  ]
}
```

## Docker building image

```shell
mvn install
cd applications/retail-cache-sink-app
mvn spring-boot:build-image
```

```shell
docker tag retail-cache-sink-app:0.0.1-SNAPSHOT cloudnativedata/retail-cache-sink-app:0.0.1-SNAPSHOT
docker push cloudnativedata/retail-cache-sink-app:0.0.1-SNAPSHOT
```