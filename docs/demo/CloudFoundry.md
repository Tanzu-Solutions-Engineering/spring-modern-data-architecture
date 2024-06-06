


```sql
select * 
from retail.customer_orders
```



```sql
select * 
from retail.products
```

cf org-quotas

cf set-org-quota data-showcase runaway

```json
[
  {
    "id": "sku1",
    "name": "Peanut butter"
  },
  {
    "id": "sku2",
    "name": "Jelly"
  },
  {
    "id": "sku3",
    "name": "Bread"
  },
  {
    "id": "sku4",
    "name": "Milk"
  }
]
```

```shell
cf logs retail-web-app
```



cf update-org-quota default -n default-medium -m 4096M -i 2048M -l 8K -r 20 -s 20 --allow-paid-service-plans

Register


```properties
sink.retail-analytics=https://github.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/releases/download/cloud-foundry-6-5-2024/retail-analytics-app-0.0.3-SNAPSHOT.jar
source.retail-source=https://github.com/Tanzu-Solutions-Engineering/spring-modern-data-architecture/releases/download/cloud-foundry-6-5-2024/retail-source-app-0.0.2-SNAPSHOT.jar
```

```properties
deployer.retail-analytics.cloudfoundry.env=JBP_CONFIG_OPEN_JDK_JRE='{ jre: { version: 17.+}}'
deployer.retail-source.cloudfoundry.env=JBP_CONFIG_OPEN_JDK_JRE='{ jre: { version: 17.+}}'
deployer.retail-analytics.bootVersion=3
deployer.retail-source.bootVersion=3
```


cf set-env  HnpEBN0-retail-source-retail-analytics-v3 JBP_CONFIG_OPEN_JDK_JRE '{ jre: { version: 17.+}}'
cf set-env  HnpEBN0-retail-source-retail-source-v3 JBP_CONFIG_OPEN_JDK_JRE "{ jre: { version: 17.+}}"

cf bind-service HnpEBN0-retail-source-retail-analytics-v3  retail-sql


```csv
"1","jsmith","sku11","1"
"1","jsmith","sku21","1"

```


```json
select data->>'id' as id, data->>'name' as name from retail.products
```

```sql
select distinct p.data from 
   ( SELECT c.original_SKU as original_SKU, c.bought_with as bought_with, count(*) as times_bought_together 
   FROM ( SELECT a.product_id as original_SKU, b.product_id as bought_with 
   FROM retail.customer_orders a 
       INNER join retail.customer_orders b ON a.order_id = b.order_id AND a.product_id != b.product_id 
       ) 
   c GROUP BY c.original_SKU, c.bought_with having original_SKU in ('s1', 's2') and 
   bought_with not in ('s1', 's2') ORDER BY times_bought_together desc FETCH FIRST 10 rows only) 
   top_associations, (select product_id, sum(quantity) as product_cnt from retail.customer_orders 
   group by product_id) count_by_product, products p where count_by_product.product_id = 
   top_associations.original_SKU and cast(top_associations.times_bought_together as 
   double precision)/ cast(count_by_product.product_cnt as double precision) > 0.09 and 
   p.id = top_associations.bought_with
```