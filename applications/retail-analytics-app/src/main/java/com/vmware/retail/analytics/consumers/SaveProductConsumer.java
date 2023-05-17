/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.repository.ProductRepository;
import com.vmware.retail.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public record SaveProductConsumer(ProductRepository repository)
        implements Consumer<List<Product>> {
    @Override
    public void accept(List<Product> products) {
        repository.saveProducts(products);
    }
}
