///*
// *
// *  * Copyright 2023 VMware, Inc.
// *  * SPDX-License-Identifier: GPL-3.0
// *
// */
//
//package com.vmware.retail.caching;
//
//import com.vmware.retail.repository.CustomerFavoriteRepository;
//import nyla.solutions.core.util.Text;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//import java.util.Calendar;
//
///**
// * RedisConfig
// *
// * @author Gregory Green
// */
//@Configuration
//@EnableRedisRepositories(basePackageClasses = CustomerFavoriteRepository.class)
//@Profile("redis")
//public class RedisConfig
//{
//    @Value("${spring.application.name}")
//    private String applicationName;
//
//    @Bean
//    public RedisSerializer redisSerializer()
//    {
//        return new GenericJackson2JsonRedisSerializer();
//    }
//
//    /**
//     * Type safe representation of application.properties
//     */
//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory, RedisSerializer redisSerializer)
//    {
//        var template = new RedisTemplate();
//        template.setDefaultSerializer(redisSerializer);
//        template.setConnectionFactory(connectionFactory);
//
//        return template;
//    }
//
//
//    @Bean
//    ApplicationContextAware listener(RedisTemplate<String,String> redisTemplate)
//    {
//        return context -> {
//            redisTemplate.opsForValue().set(applicationName,
//                    Text.formatDate(Calendar.getInstance().getTime()));
//        };
//    }
//
//}
