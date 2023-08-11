# retail-analytics-app

Performance and database analytics.
This application uses an event driven design based on messages consumed in RabbitMQ.


Verified databases

- [VMware Postgres](https://docs.vmware.com/en/VMware-Postgres/index.html)
- [VMware MYSQL](https://docs.vmware.com/en/VMware-SQL-with-MySQL-for-Tanzu-Application-Service/3.0/mysql-for-tas/index.html)
- [VMware Greenplum](https://www.vmware.com/products/greenplum.html)


# Configuration

Set active Profile 


| profile        | Notes                                                                                   |
|----------------|-----------------------------------------------------------------------------------------|
 | postgres       | Used Postgres                                                                           |
 | mysql          | Used MySQL                                                                              | 
 | product-quorum | Used RabbitMQ [Quorum Queues](https://www.rabbitmq.com/quorum-queues.html) for Products |
 | product-stream | Used RabbitMQ [Streams](https://www.rabbitmq.com/streams.html for Products              |


Example 
```shell
java -jar applications/retail-analytics-app/target/retail-analytics-app-0.0.3-SNAPSHOT.jar --spring.profiles.active=postgres,local-postgres,product-quorum
```

# Misc.

## Useful Postgres SQL

List connections
```
 SELECT usename,application_name,pid FROM pg_stat_activity;
```

Get current connection information

```
\conninfo
```


```shell
CREATE USER retail WITH PASSWORD 'retail';
create schema retail;
ALTER USER retail SET search_path TO retail;
```

```shell
GRANT ALL PRIVILEGES ON SCHEMA public TO retail;
GRANT ALL PRIVILEGES ON SCHEMA retail TO retail;
```

```shell
GRANT ALL PRIVILEGES ON TABLE products TO gregoryg;
```

```shell
select * from pg_tables where tablename = 'products';
```

# Docker 

## Docker building image

```shell
mvn install
cd applications/retail-analytics-app
mvn spring-boot:build-image
```

```shell
docker tag retail-analytics-app:0.0.3-SNAPSHOT cloudnativedata/retail-analytics-app:0.0.3-SNAPSHOT
docker push cloudnativedata/retail-analytics-app:0.0.3-SNAPSHOT
```
--------------

# Testing

## Orders

Exchange: retail.customer.orders
routing_key: nyla

```json
{
  "id" : 999,
  "customerIdentifier": {"customerId" :  "nyla"},
  "productOrders" : [
    {
      "productId" : "sku1",
      "quantity" : 1
    }
    
  ]
}
```
## Customer Favorites

```json
{
  "customerId" : "nyla"
  
}
```


# Save Product Consumer

Exchange: retail.products
HEADER: contentType=application/json

```json
[{"id":  "sku1", "name" : "Peanut butter"}]
```


# MYSQL 

Example SQL 

```roomsql
SELECT data, total_quantity
from products p,
   (SELECT sum(quantity) total_quantity,
        product_id
FROM customer_orders
WHERE customer_id = 'nyla'
GROUP BY product_id order by total_quantity
DESC
limit 10 ) top_orders
WHERE p.id = top_orders.product_id;
```



```roomsql
select distinct p.data
                from (
                SELECT c.original_SKU as original_SKU, c.bought_with as bought_with, count(*) as times_bought_together
                FROM (
                  SELECT a.product_id as original_SKU, b.product_id as bought_with
                  FROM customer_orders a
                  INNER join customer_orders b
                  ON a.order_id = b.order_id AND a.product_id != b.product_id ) c
                GROUP BY c.original_SKU, c.bought_with
                having original_SKU in ('sku1')  and bought_with not in ('sku1')
                ORDER BY times_bought_together desc
                limit 10) top_associations,
                (select product_id, sum(quantity) as product_cnt
                      from customer_orders
                      group by product_id) count_by_product,
                products p
                where count_by_product.product_id = top_associations.original_SKU
                and cast(top_associations.times_bought_together as double precision)/
                cast(count_by_product.product_cnt as  double precision) > 99
                and  p.id = top_associations.bought_with
```