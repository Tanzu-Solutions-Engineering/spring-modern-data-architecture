/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.service.impl;

import com.vmware.retail.analytics.entity.CustomerOrderEntity;
import com.vmware.retail.analytics.entity.ProductOrderEntity;
import com.vmware.retail.analytics.repository.CustomerOrderRepository;
import com.vmware.retail.analytics.service.CustomerAnalyticService;
import com.vmware.retail.analytics.service.CustomerOrderService;
import com.vmware.retail.domain.order.CustomerOrder;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerOrderDataService implements CustomerOrderService {

    private final CustomerOrderRepository repository;
    private final CustomerAnalyticService customerAnalyticService;

    @Override
    @Transactional
    public void saveOrder(CustomerOrder customerOrder) {
        repository.saveAll(customerOrder.productOrders().stream()
                .map(po -> CustomerOrderEntity
                        .builder()
                        .orderId(
                                customerOrder.id())
                        .customerId(
                                customerOrder.customerIdentifier().customerId())
                        .productOrder(new ProductOrderEntity(po.productId(), po.quantity())
                        ).build()
                ).toList());

        customerAnalyticService.constructFavorites(customerOrder.customerIdentifier());
        customerAnalyticService.publishPromotion(customerOrder);
    }
}
