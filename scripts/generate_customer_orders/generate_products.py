#  /*
#   * Copyright 2023 VMware, Inc.
#   * SPDX-License-Identifier: GPL-3.0
#   */


# Example
# INSERT INTO products(id, data)
# VALUES ('sku4','{"id" : "sku4", "name" : "Milk"}');

products = []

with open('/Users/Projects/VMware/Tanzu/TanzuData/Spring/dev/spring-modern-data-architecture/scripts/generate_customer_orders/list/products.txt') as f:
    products = f.read().splitlines()

# orderCnt = 10
productId = 1
# while productId < orderCnt:

with open('scripts/generate_customer_orders/resources/products.json', 'w') as f:
  f.write('[\n')

  for product in products:
    sku = 'sku'+str(productId)

    f.write('\t{"id" : "'+sku+'", "name" : "'+product+'"}')

    if productId != 558:
      # f.write('VALUES (\''+sku+'\',\'{"id" : "'+sku+'", "name" : "'+product+'"}\');')
      f.write(',\n')

    productId += 1

  f.write('\n]')