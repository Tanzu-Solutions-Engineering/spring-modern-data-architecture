/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.controller;

import com.vmware.retail.source.controller.exceptions.InvalidOrderCsvException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
@Slf4j
public class CustomerOrderController {

    private final Consumer<String> splitCsvByCustomerOrder;

    @PostMapping
    @RequestMapping("csv")
    public void processCsvOrders(@RequestBody String csvOrders) {
        try {
            splitCsvByCustomerOrder.accept(csvOrders);
        }
        catch(NumberFormatException e)
        {
            log.error("{}",e);
            throw new InvalidOrderCsvException(e);
        }
    }
}
