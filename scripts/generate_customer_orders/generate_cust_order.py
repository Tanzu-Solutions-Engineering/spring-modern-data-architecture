#  /*
#   * Copyright 2023 VMware, Inc.
#   * SPDX-License-Identifier: GPL-3.0
#   */


# Example
# private final int orderCol = 0;
# private final int customerCol  = 1;
# private final int productIdCol = 2;
# private final int quantityCol = 3;
#      * "3","c1","pc","5"
#      * "3","c1","pb","3"

customers = ["nyla", "morgan", "demetrius"]
products = ["apple", "banana", "cherry"]

orderCnt = 10
orderId = 1
while orderId < orderCnt:
    for customer in customers:
        for product in products:
            print('"'+str(orderId)+'","'+customer+'","'+product+'","1"')

    orderId += 1
