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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CustomerAnalyticsDataService implements CustomerAnalyticService {
    private final RabbitTemplate rabbitTemplate;
    private final ProductRepository productRepository;
    private final int topCount;

    private final String customerFavoritesExchange;

    public CustomerAnalyticsDataService(RabbitTemplate rabbitTemplate,
                                        ProductRepository productRepository, RedisTemplate<String, Promotion> redisTemplate,
                                        @Value("${retail.favorites.top.count}") int topCount,
                                        @Value("${retail.customer.favorites.exchange:retail.customer.favorites}")
                                        String customerFavoritesExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.productRepository = productRepository;
        this.topCount = topCount;
        this.customerFavoritesExchange = customerFavoritesExchange;
    }


    @Async
    @Override
    public void constructFavorites(CustomerIdentifier customerIdentifier) {
        var customerId = customerIdentifier.customerId();
        var customerFavorites = productRepository.findCustomerFavoritesByCustomerIdAndTopCount(customerId, topCount);

        this.rabbitTemplate.convertAndSend(customerFavoritesExchange,customerIdentifier.customerId(),customerFavorites);
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

        //this.redisTemplate.convertAndSend(customerId,promotion);
        this.rabbitTemplate.convertAndSend(customerFavoritesExchange,customerIdentifier.customerId(),customerFavorites);

        return promotion;

    }
}
