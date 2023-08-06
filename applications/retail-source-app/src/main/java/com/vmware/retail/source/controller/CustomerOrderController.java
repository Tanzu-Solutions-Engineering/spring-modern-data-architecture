/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
@RequestMapping("retail")
public class CustomerOrderController {

    private final Consumer<String> splitCsvByCustomerOrder;

    @PostMapping("orders")
    public void processCsvOrders(@RequestBody String csvOrders) {
        splitCsvByCustomerOrder.accept(csvOrders);
    }
}
