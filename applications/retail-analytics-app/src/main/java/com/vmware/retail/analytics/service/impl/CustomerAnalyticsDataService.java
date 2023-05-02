/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.service.impl;

import com.vmware.retail.analytics.repository.CustomerFavoriteRepository;
import com.vmware.retail.analytics.repository.ProductRepository;
import com.vmware.retail.analytics.service.CustomerAnalyticService;
import com.vmware.retail.domain.Promotion;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import com.vmware.retail.domain.order.CustomerOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CustomerAnalyticsDataService implements CustomerAnalyticService {
    private final CustomerFavoriteRepository customerFavoriteRepository;
    private final ProductRepository productRepository;
    private final int topCount;

    private final RedisTemplate<String,Promotion> redisTemplate;

    public CustomerAnalyticsDataService(CustomerFavoriteRepository customerFavoriteRepository, ProductRepository productRepository, RedisTemplate<String, Promotion> redisTemplate,
                                        @Value("${retail.favorites.top.count}") int topCount) {
        this.customerFavoriteRepository = customerFavoriteRepository;
        this.productRepository = productRepository;
        this.topCount = topCount;
        this.redisTemplate = redisTemplate;
    }


    @Async
    @Override
    public void constructFavorites(CustomerIdentifier customerIdentifier) {
        var customerId = customerIdentifier.customerId();
        var customerFavorites = productRepository.findCustomerFavoritesByCustomerIdAndTopCount(customerId, topCount);

        this.customerFavoriteRepository.save(customerFavorites);
    }

    /**
     * Determine products frequently both together and send
     * @param customerOrder
     * @return
     */
    @Async
    @Override
    public Promotion publishPromotion(CustomerOrder customerOrder) {
        var recommendations = this.productRepository.findFrequentlyBoughtTogether(customerOrder.productOrders());
        var customerId = customerOrder.customerIdentifier().customerId();
        var promotion = new Promotion(customerId,
                null,recommendations);

        this.redisTemplate.convertAndSend(customerId,promotion);

        return promotion;

    }
}
