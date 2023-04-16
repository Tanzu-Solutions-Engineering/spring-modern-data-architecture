/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source;

import com.vmware.retail.domain.customer.CustomerIdentifier;
import com.vmware.retail.domain.order.CustomerOrder;
import com.vmware.retail.domain.order.ProductOrder;
import com.vmware.retail.source.functions.CsvToCustomerOrder;
import nyla.solutions.core.exception.TooManyRowsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nyla.solutions.core.util.Organizer.toList;
import static org.assertj.core.api.Assertions.assertThat;


class CsvToCustomerOrderTest {

    private CsvToCustomerOrder subject;

    @BeforeEach
    void setUp() {
        subject = new CsvToCustomerOrder();
    }

    @Test
    void given_validCsv3Item_when_apply_then_returnCustomOrderWith3Records() {

        Long orderId = 3L;
        String expectedCustomerIdentifier = "c1";
        CustomerIdentifier customIdentifier = new CustomerIdentifier(expectedCustomerIdentifier);
        ProductOrder p1 = new ProductOrder("pc",5);
        ProductOrder p2 = new ProductOrder("pb",3);
        List<ProductOrder> produceOrders = toList(p1,p2);
        CustomerOrder expected = new CustomerOrder(orderId,customIdentifier,produceOrders);

        var csv = """
                "3","c1","pc","5"
                "3","c1","pb","3"
                """;

        subject = new CsvToCustomerOrder();

        CustomerOrder actual = subject.apply(csv);

        assertThat(expected.customerIdentifier()).isEqualTo(actual.customerIdentifier());
        assertThat(expected.id()).isEqualTo(actual.id());
         assertThat(expected.productOrders().get(0)).isIn(actual.productOrders());
        assertThat(expected.productOrders().get(1)).isIn(actual.productOrders());
    }

    @Test
    void given_csvWith_multiple_customers_when_apply_then_throws_Exception_cannot_handle_customers() {
        var csv = """
                "3","c2","pc","5"
                "3","c1","pb","3"
                """;

        Assertions.assertThrows(TooManyRowsException.class,() -> subject.apply(csv));
    }

    @Test
    void given_csvWith_multiple_orderIds_when_apply_then_throws_Exception_cannot_handle_customers() {
        var csv = """
                "2","c2","pc","5"
                "3","c2","pb","3"
                """;

        Assertions.assertThrows(TooManyRowsException.class,() ->subject.apply(csv));
    }
}