/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.Product;
import com.vmware.retail.domain.Promotion;
import com.vmware.retail.events.CacheListerConsumerBridge;
import com.vmware.retail.repository.gemfire.CustomerFavoriteGemFireRepository;
import com.vmware.retail.repository.gemfire.ProductGemFireRepository;
import com.vmware.retail.repository.gemfire.PromotionGemFireRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.client.Interest;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import java.util.function.Consumer;


@Profile("gemfire")
@EnableGemfireRepositories(basePackageClasses = {CustomerFavoriteGemFireRepository.class,
        PromotionGemFireRepository.class, ProductGemFireRepository.class})
@Configuration
@Slf4j
@ClientCacheApplication(subscriptionEnabled = true)
public class GemFireConfig {

    @Value("${spring.data.gemfire.pool.default.locators}")
    private String locators;

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

    @Bean
    CacheListener<String, Promotion> listener(Consumer<Promotion> promotionConsumer){
        return new CacheListerConsumerBridge(promotionConsumer);
    }

    @Bean("Promotion")
    public ClientRegionFactoryBean<String, Promotion> promotion(GemFireCache gemFireCache,
                                                                CacheListener<String, Promotion> listener){
        var factory = new ClientRegionFactoryBean<String, Promotion>();
        factory.setName("Promotion");
        factory.setCache(gemFireCache);
        factory.setDataPolicy(DataPolicy.EMPTY);
        CacheListener<String, Promotion>[] listeners = new CacheListener[]{listener};
        factory.setCacheListeners(listeners);
        Interest<String> interest = Interest.newInterest(Interest.ALL_KEYS);
        Interest<String>[] interests = new Interest[]{interest};
        factory.setInterests(interests);
        return factory;
    }
}
