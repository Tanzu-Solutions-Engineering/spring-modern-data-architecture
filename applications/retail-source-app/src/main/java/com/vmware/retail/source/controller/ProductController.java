/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.controller;

import com.vmware.retail.domain.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final RabbitTemplate template;

    private final String exchange;
    private final String routingKey;

    public ProductController(RabbitTemplate template,
                             @Value("${retail.products.exchange:retail.products}")
                             String exchange,
                             @Value("${retail.products.routingKey:}")
                             String routingKey) {
        this.template = template;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @PostMapping
    public void loadProducts(@RequestBody List<Product> products) {
        template.convertAndSend(exchange,routingKey,products);
    }
}
