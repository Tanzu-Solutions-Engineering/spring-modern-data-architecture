/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.caching.consumers;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.ProductQuantity;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.TreeSet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveFavoritesConsumerTest {
    @Mock
    private CustomerFavoriteRepository customerAnalyticRepository;
    private SaveFavoritesConsumer subject;

    @BeforeEach
    void setUp() {
        subject = new SaveFavoritesConsumer(customerAnalyticRepository);
    }

    @Test
    void given_customer_when_accept_then_cacheFavorites() {
        String id = "u01";

        var productQuantities = new TreeSet<ProductQuantity>();
        var productQuantity = JavaBeanGeneratorCreator.of(ProductQuantity.class).create();

        productQuantities.add(productQuantity);
        CustomerFavorites customIdentifier = new CustomerFavorites(id,productQuantities);

        subject.accept(customIdentifier);

        verify(customerAnalyticRepository).save(any(CustomerFavorites.class));
    }
}