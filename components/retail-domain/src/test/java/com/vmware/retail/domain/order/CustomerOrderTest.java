/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.domain.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class CustomerOrderTest {

    @Test
    void serialization() throws JsonProcessingException {
        /*""
                 { "id" : 1,
                  "customerIdentifier": { "customerId" :  "nyla",
                          "productOrders" : \s
                              [
                                {"productId" :  "sku1", "quantity" : 1},
                                {"productId" :  "sku2", "quantity" : 1},
                                {"productId" :  "sku3", "quantity" : 1}
                              ]
                          }
                   }
                """;*/
        var mapper = new ObjectMapper();
        var expected = new CustomerOrder(1L,new CustomerIdentifier("nyla"), asList(new ProductOrder("sku11",1)));
        var json = mapper.writeValueAsString(expected);

        System.out.println("json:"+json);
        var actual = mapper.readValue(json,CustomerOrder.class);

        assertEquals(expected, actual);
    }
}