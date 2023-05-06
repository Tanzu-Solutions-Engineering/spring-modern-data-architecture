/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics;

import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;


    @Value("${spring.rabbitmq.username:guest}")
    private String username = "guest";

    @Value("${spring.rabbitmq.password:guest}")
    private String password   = "guest";

    @Value("${spring.rabbitmq.host:127.0.01}")
    private String hostname = "localhost";

    @Value("${spring.cloud.stream.bindings.saveProductConsumer-in-0.destination}.${spring.cloud.stream.bindings.saveProductConsumer-in-0.group}")
    private String streamName;


    @Value("${retail.customer.favorites.exchange:retail.customer.favorites}")
    private String customerFavoritesExchangeName;

    @Value("${retail.customer.promotions.exchange:retail.customer.promotions}")
    private String promotionExchangeName;

    @Bean
    public Exchange customerFavoritesExchange()
    {
        return new TopicExchange(customerFavoritesExchangeName);
    }

    @Bean
    public Exchange promotionExchange()
    {
        return new TopicExchange(promotionExchangeName);
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

    @Bean
    Environment rabbitStreamEnvironment() {

        var env = Environment.builder()
                .host(hostname)
                .username(username)
                .password(password)
                .clientProperty("id",applicationName)
                .build();

        env.streamCreator().stream(streamName).create();

        return env;

    }

    @Bean
    @ConditionalOnProperty(name = "rabbitmq.streaming.replay",havingValue = "true")
    ListenerContainerCustomizer<MessageListenerContainer> customizer() {
        return ( MessageListenerContainer cont, String dest, String group) ->
        {
            if(!(cont instanceof  StreamListenerContainer))
                return;

            final StreamListenerContainer container = StreamListenerContainer.class.cast(cont);

            log.info("Replaying, setting offset to first the record for streams");
            container.setConsumerCustomizer( (name, builder) -> {
                builder.offset(OffsetSpecification.first());
            });
        };
    }
}
