/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.service.CustomerAnalyticService;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CalculateFavoritesConsumerTest {
    @Mock
    private CustomerAnalyticService customerAnalyticService;
    private CalculateFavoritesConsumer subject;

    @BeforeEach
    void setUp() {
        subject = new CalculateFavoritesConsumer(customerAnalyticService);
    }

    @Test
    void given_customer_when_accept_then_cacheFavorites() {
        String customerId = "u01";

        CustomerIdentifier customIdentifier = new CustomerIdentifier(customerId);

        subject.accept(customIdentifier);

        verify(customerAnalyticService).constructFavorites(any());
    }

}