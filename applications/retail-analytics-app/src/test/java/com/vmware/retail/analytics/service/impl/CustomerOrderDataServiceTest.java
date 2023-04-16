/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.service.impl;

import com.vmware.retail.analytics.repository.CustomerOrderRepository;
import com.vmware.retail.analytics.service.CustomerAnalyticService;
import com.vmware.retail.domain.order.CustomerOrder;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerOrderDataServiceTest {
    @Mock
    private CustomerOrderRepository customerOrderRepository;


    @Mock
    private CustomerAnalyticService customerAnalyticService;
    private CustomerOrderDataService subject;

    @BeforeEach
    void setUp() {
        subject = new CustomerOrderDataService(customerOrderRepository,customerAnalyticService);
    }

    @Test
    void saveOrder() {

        var customerOrder = JavaBeanGeneratorCreator.of(CustomerOrder.class).create();

        subject.saveOrder(customerOrder);

        verify(customerOrderRepository).saveAll(any());
        verify(customerAnalyticService).constructFavorites(any());
        verify(customerAnalyticService).publishPromotion(any(CustomerOrder.class));
    }
}