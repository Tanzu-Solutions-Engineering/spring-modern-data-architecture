/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.gemfire.spring.locator;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.LocatorApplication;
import org.springframework.data.gemfire.config.annotation.LocatorConfigurer;

import java.util.Properties;


@Configuration
@LocatorApplication
public class GemFireConfig {
    @Value("${spring.data.gemfire.locator.name}")
    private String locatorName;

    @Value("${spring.data.gemfire.locator.port}")
    private int port;

    @Value("${gemfire.remote-locators:}")
    private String remoteLocators;


    @Value("${gemfire.jmx-manager-port:1090}")
    private String jmxManagerPort;

    @Value("${gemfire.tcp-port:11001}")
    private String tcpPort;

    @Value("${gemfire.membership-port-range:10901-10910}")
    private String membershipPortRange;

    @Value("${gemfire.http-service-port:18080}")
    private String httpServicePort;
    @Value("${gemfire.distributed-system-id}")
    private String distributeSystemId;

    /**
     * --gemfire.remote-locators=10100 --=gemfire.deploy-working-dir=runtime/gf1
     * @return
     */
    @Bean
    LocatorConfigurer locatorConfigurer()
    {
        return (name, bean) -> {
            var gemfireProperties = new Properties();
            gemfireProperties.setProperty("remote-locators",remoteLocators);
            gemfireProperties.setProperty("jmx-manager-port",jmxManagerPort);
            gemfireProperties.setProperty("distributed-system-id",distributeSystemId);
            gemfireProperties.setProperty("tcp-port",tcpPort);
            gemfireProperties.setProperty("membership-port-range",membershipPortRange);
            gemfireProperties.setProperty("http-service-port",httpServicePort);

            bean.setGemFireProperties(gemfireProperties);
        };

    }
}
