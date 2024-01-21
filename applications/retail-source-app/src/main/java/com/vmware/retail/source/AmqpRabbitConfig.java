/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpRabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${retail.products.exchange:retail.products}")
    private String productExchange;

    @Value("${source.splitCsv.customer.orders.exchange:retail.customer.orders}")
    private String customerOrderExchange;

    @Bean
    TopicExchange productExchange(){
        return new TopicExchange(productExchange);
    }

    @Bean
    TopicExchange customerOrderExchange(){
        return new TopicExchange(customerOrderExchange);
    }

    @Bean
    ConnectionNameStrategy connectionNameStrategy(){
        return (connectionFactory) -> applicationName;
    }

    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }
}
