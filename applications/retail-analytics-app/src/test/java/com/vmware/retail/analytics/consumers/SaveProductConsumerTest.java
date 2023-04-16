/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.repository.ProductRepository;
import com.vmware.retail.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveProductConsumerTest {

    @Mock
    private ProductRepository repository;

    @Test
    void given_products_when_accept_when_save_to_database() {

        var subject = new SaveProductConsumer(repository);
        List<Product> products = asList(new Product("id","name"));

        subject.accept(products);

        verify(repository).saveProducts(any(List.class));


    }
}