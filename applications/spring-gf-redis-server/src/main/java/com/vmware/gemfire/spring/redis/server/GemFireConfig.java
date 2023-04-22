/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.gemfire.spring.redis.server;


import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;


@Configuration
@CacheServerApplication
public class GemFireConfig {

//    @Value("${spring.data.gemfire.locators}")
//    String locators;

    @Bean
    PartitionedRegionFactoryBean<String,Object> region(GemFireCache gemFireCache)
    {
        var region = new PartitionedRegionFactoryBean<String,Object>();
        region.setCache(gemFireCache);
        region.setDataPolicy(DataPolicy.PARTITION);
        region.setPersistent(false);
        region.setName("GF_REDIS_REGION");
        return region;

    }
}
