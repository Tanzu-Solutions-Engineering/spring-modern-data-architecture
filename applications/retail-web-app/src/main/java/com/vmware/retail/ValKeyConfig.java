/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail;

import com.vmware.retail.domain.Promotion;
import spring.modern.data.repository.valkey.CustomerFavoriteValKeyRepository;
import spring.modern.data.repository.valkey.ProductValKeyRepository;
import spring.modern.data.repository.valkey.PromotionValKeyRepository;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.util.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Calendar;
import java.util.function.Consumer;



/**
 * RedisConfig
 *
 * @author Gregory Green
 */
@Configuration
@EnableRedisRepositories(basePackageClasses = {
        CustomerFavoriteValKeyRepository.class,
        ProductValKeyRepository.class,
        PromotionValKeyRepository.class
})
@Profile("valkey")
@Slf4j
public class ValKeyConfig
{
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${retail.promotion.listener.pattern.topic}")
    private String patternTopic;

    public ValKeyConfig()
    {
        log.info("Config Valkey");

    }

    @Bean
    public RedisSerializer redisSerializer()
    {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory, RedisSerializer redisSerializer)
    {
        var template = new RedisTemplate();
        template.setDefaultSerializer(redisSerializer);
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(Consumer<Promotion> listener,
                                                                       RedisConnectionFactory connectionFactory,
                                                                       RedisSerializer redisSerializer)
    {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        MessageListenerAdapter messageListener = new MessageListenerAdapter(listener);
        messageListener.setDefaultListenerMethod("accept");
        messageListener.setSerializer(redisSerializer);
        messageListener.afterPropertiesSet();
        container.addMessageListener(messageListener,PatternTopic.of(this.patternTopic));
        return container;
    }

    @Bean
    ApplicationContextAware listener(RedisTemplate<String,String> redisTemplate)
    {
        return context -> {
            redisTemplate.opsForValue().set(applicationName,
                    Text.formatDate(Calendar.getInstance().getTime()));
        };
    }
}
