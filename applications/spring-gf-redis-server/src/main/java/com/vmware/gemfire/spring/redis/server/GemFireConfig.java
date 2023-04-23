/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.gemfire.spring.redis.server;


import com.vmware.gemfire.redis.internal.data.RedisData;
import com.vmware.gemfire.redis.internal.data.RedisKey;
import com.vmware.gemfire.redis.internal.data.RedisString;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionShortcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.PeerRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.RegionConfigurer;


/**
 * spring.data.gemfire.locators
 */
@Configuration
@CacheServerApplication
@Slf4j
public class GemFireConfig {

    /**
     * REGION_NAME = "GF_REDIS_REGION"
     */
    private static final String REGION_NAME = "GF_REDIS_REGION";

    /**
     * PARTITION
     * PARTITION_REDUNDANT
     */
    @Value("${gemfire.redis.region.type:PARTITION}")
    private String regionTypeName;

    /**
     *
     * @param gemFireCache the GemFire cache
     * @return the factory to create the Redis region
     */
    @Bean
    PartitionedRegionFactoryBean<String,Object> region(GemFireCache gemFireCache)
    {

        log.info("Create region {} with regionTypeName {}",REGION_NAME,regionTypeName);

        var region = new PartitionedRegionFactoryBean<String,Object>();

        region.setCache(gemFireCache);
        region.setDataPolicy(DataPolicy.PARTITION);

        region.setPersistent(false);
        region.setName(REGION_NAME);

        var configurer = new RegionConfigurer() {
            @Override
            public void configure(String beanName, PeerRegionFactoryBean<?, ?> bean) {
                bean.setShortcut(RegionShortcut.valueOf(regionTypeName));
            }
        };
        region.setRegionConfigurers(configurer);

        return region;
    }

    /**
     * Working around for
     * (error) MOVED 6918 192.168.1.76:6379
     * @return
     */
    @Bean
    ApplicationContextAware startupListener() {
        return applicationContext -> {
            byte[] bytes = {1};
            var region = applicationContext.getBean(Region.class);

            var key = new RedisKey(bytes);
            var data = new RedisString(bytes);

            log.info("Putting data in region {}", region);
            region.put(key, data);

        };
    }
}
