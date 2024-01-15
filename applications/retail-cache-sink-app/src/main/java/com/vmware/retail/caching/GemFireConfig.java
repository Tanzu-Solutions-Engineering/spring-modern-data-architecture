/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.caching;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.gemfire.CustomerFavoriteGemFireRepository;
import com.vmware.retail.repository.gemfire.ProductGemFireRepository;
import com.vmware.retail.repository.gemfire.PromotionGemFireRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;


@Profile("gemfire")
@EnableGemfireRepositories(basePackageClasses = {CustomerFavoriteGemFireRepository.class,
        PromotionGemFireRepository.class, ProductGemFireRepository.class})
@Configuration
@Slf4j
@ClientCacheApplication
public class GemFireConfig {

    public GemFireConfig(){
        log.info("Created");
    }

    @Bean("CustomerFavorites")
    public ClientRegionFactoryBean<String, CustomerFavorites> customerFavorites(GemFireCache gemFireCache){
        var factory = new ClientRegionFactoryBean<String, CustomerFavorites>();
        factory.setName("CustomerFavorites");
        factory.setCache(gemFireCache);
        factory.setDataPolicy(DataPolicy.EMPTY);
        return factory;
    }

    @Bean("Product")
    public ClientRegionFactoryBean<String, Product> product(GemFireCache gemFireCache){
        var factory = new ClientRegionFactoryBean<String, Product>();
        factory.setName("Product");
        factory.setCache(gemFireCache);
        factory.setDataPolicy(DataPolicy.EMPTY);
        return factory;
    }

    @Bean("Promotion")
    public ClientRegionFactoryBean<String, Promotion> promotion(GemFireCache gemFireCache){
        var factory = new ClientRegionFactoryBean<String, Promotion>();
        factory.setName("Promotion");
        factory.setCache(gemFireCache);
        factory.setDataPolicy(DataPolicy.EMPTY);
        return factory;
    }
}
