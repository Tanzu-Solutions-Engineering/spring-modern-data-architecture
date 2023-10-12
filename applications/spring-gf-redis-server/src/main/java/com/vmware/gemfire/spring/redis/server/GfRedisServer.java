/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.gemfire.spring.redis.server;

import nyla.solutions.core.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GfRedisServer {

    private static final String REDIS_PORT_PROP = "gemfire-for-redis-port";
    private static final String DEFAULT_REDIS_PORT = "6379";

    /**
     * --J=-Dgemfire-for-redis-port=6379  --J=-Dgemfire-for-redis-enabled=true
     */
    public static void main(String[] args) {

        Config.loadArgs(args);

        System.setProperty(REDIS_PORT_PROP,Config.getProperty("gemfire.gemfire-for-redis-port",DEFAULT_REDIS_PORT));
        System.setProperty("gemfire-for-redis-enabled","true");
        System.setProperty("gemfire-for-redis-use-default-region-config","false");
        System.setProperty("gemfire-for-redis-use-default-region-config","false");
        System.setProperty("gemfire-for-redis-region-name","GF_REDIS_REGION");

        SpringApplication.run(GfRedisServer.class,args);
    }
}
