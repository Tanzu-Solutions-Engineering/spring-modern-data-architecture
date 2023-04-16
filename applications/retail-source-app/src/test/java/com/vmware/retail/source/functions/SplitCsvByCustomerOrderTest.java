/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SplitCsvByCustomerOrderTest {

    private String exchange ="customerId";
    private int customerIdCol = 1;
    private int orderIdCol = 0;
    private SplitCsvByCustomerOrder subject;

    @Mock
    private CsvToCustomerOrder toCsv;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        subject = new SplitCsvByCustomerOrder(toCsv, exchange,customerIdCol, orderIdCol, rabbitTemplate);
    }

    @Test
    void given_csvOrder_when_apply_then_orderByCustomerAndGroupByOrderId() {

        var csv = """
                "3","c1","pa","5"
                "2","c2","pb","3"
                "3","c1","pc","3"
                """;

        subject.accept(csv);


        verify(toCsv,times(2)).apply(anyString());
    }

    @Test
    void given_empty_when_accept_then_doesNotThrow_Exception() {

        Assertions.assertDoesNotThrow(() -> subject.accept(null));
        Assertions.assertDoesNotThrow(() -> subject.accept(" "));
        Assertions.assertDoesNotThrow(() -> subject.accept(""));
    }
}