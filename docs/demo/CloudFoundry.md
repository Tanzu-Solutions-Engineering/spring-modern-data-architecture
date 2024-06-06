


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
