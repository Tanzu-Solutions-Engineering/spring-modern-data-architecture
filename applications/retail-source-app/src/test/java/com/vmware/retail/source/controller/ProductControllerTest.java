/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source.controller;

import com.vmware.retail.domain.Product;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private ProductController subject;

    @Mock
    private RabbitTemplate template;
    private List<Product> products = asList(
            JavaBeanGeneratorCreator.of(Product.class).create(),
            JavaBeanGeneratorCreator.of(Product.class).create()
    );
    private String exchange = "exchange";
    private String routingKey = "";

    @BeforeEach
    void setUp() {
        subject = new ProductController(template,exchange,routingKey);
    }

    @Test
    void loadProducts() {
        subject.loadProducts(products);

        verify(template).convertAndSend(anyString(),anyString(),any(Object.class));
    }
}