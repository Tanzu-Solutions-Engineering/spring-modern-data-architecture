/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.service.CustomerOrderService;
import com.vmware.retail.domain.order.CustomerOrder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

    @Component
    public record OrderConsumer
            (CustomerOrderService customerOrderDataService)
            implements Consumer<CustomerOrder> {
        @Override
        public void accept(CustomerOrder customerOrder) {
            customerOrderDataService.saveOrder(customerOrder);

        }
    }
