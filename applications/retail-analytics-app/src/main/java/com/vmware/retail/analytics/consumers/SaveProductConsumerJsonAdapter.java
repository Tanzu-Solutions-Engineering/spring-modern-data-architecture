/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmware.retail.domain.Product;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
@AllArgsConstructor
public class SaveProductConsumerJsonAdapter implements Consumer<String> {

    private final SaveProductConsumer consumer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void accept(String productListJson) {
        var listOfProducts = objectMapper.readValue(productListJson,new TypeReference<List<Product>>(){});
        consumer.accept(listOfProducts);

    }
}
