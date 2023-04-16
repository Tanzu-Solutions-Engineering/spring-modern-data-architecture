/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.functions;

import com.vmware.retail.domain.customer.CustomerIdentifier;
import com.vmware.retail.domain.order.CustomerOrder;
import com.vmware.retail.domain.order.ProductOrder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.exception.TooManyRowsException;
import nyla.solutions.core.io.csv.CsvReader;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static nyla.solutions.core.util.Organizer.getByIndex;

@Slf4j
@Component
public class CsvToCustomerOrder implements Function<String, CustomerOrder> {

    private final int orderCol = 0;
    private final int customerCol  = 1;
    private final int productIdCol = 2;
    private final int quantityCol = 3;

    /**
     * Example CSV
     *
     * "3","c1","pc","5"
     * "3","c1","pb","3"
     * @param csv the function argument
     * @return
     */
    @SneakyThrows
    @Override
    public CustomerOrder apply(String csv) {

      log.info("csv:\n {}",csv);

      var csvReader = new CsvReader(new StringReader(csv));

      var productOrders = new ArrayList(csvReader.size());

      String productId,quantityText,rowOrderIdText =
              null,rowCustomerId = null;

        String customerId = null;
        String orderId = null;

        for (List<String> row : csvReader){
          rowOrderIdText = getByIndex(row,orderCol);
          if(orderId != null && !orderId.equals(rowOrderIdText))
              throw new TooManyRowsException("Cannot process multiple orderId(s) ("+orderId+","+rowOrderIdText+")");

          orderId = rowOrderIdText;

          rowCustomerId = getByIndex(row,customerCol);
          if(customerId != null && !customerId.equals(rowCustomerId))
              throw new TooManyRowsException("Cannot process multiple customerId(s) ("+customerId+","+rowCustomerId+")");

          customerId = rowCustomerId;

          productId = getByIndex(row,productIdCol);
          quantityText = getByIndex(row, quantityCol);

          productOrders.add(new ProductOrder(productId, Integer.valueOf(quantityText)));
        }

      return new CustomerOrder(Long.valueOf(rowOrderIdText),
              new CustomerIdentifier(rowCustomerId),
              productOrders);
    }
}
