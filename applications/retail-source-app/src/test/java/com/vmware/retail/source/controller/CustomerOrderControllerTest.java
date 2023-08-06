/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.controller;

import com.vmware.retail.source.controller.exceptions.InvalidOrderCsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerOrderControllerTest {

    private String csvOrders = """
"3","c1","pa","5"
"2","c2","pb","3"
"3","c1","pc","3"
            """;
    @Mock
    private Consumer<String> splitCsvByCustomerOrder;

    private CustomerOrderController subject;

    @BeforeEach
    void setUp() {
        subject = new CustomerOrderController(splitCsvByCustomerOrder);
    }

    @DisplayName("GIVEN orders cvs to WHEN accept THEN call func split by customer Order")
    @Test
    void orderToSplit() {
        subject.processCsvOrders(csvOrders);

        verify(splitCsvByCustomerOrder).accept(anyString());
    }

    @DisplayName("GIVEN csv with invalid order WHEN process THEN throw InvalidOrderCsvException")
    @Test
    void formatException() {

        var invalidOrderIdCsv = """
                "InvalidId","user","productId","quantity"
                """;

       doThrow(new NumberFormatException("InvalidId"))
               .when(splitCsvByCustomerOrder).accept(anyString());

        assertThrows(InvalidOrderCsvException.class, () -> subject.processCsvOrders(invalidOrderIdCsv));

    }
}