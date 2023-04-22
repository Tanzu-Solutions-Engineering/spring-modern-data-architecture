/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.gemfire.spring.locator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GfLocatorServer {

    /**
     * --J=-Dgemfire-for-redis-port=6379  --J=-Dgemfire-for-redis-enabled=true
     */
    public static void main(String[] args) {

        SpringApplication.run(GfLocatorServer.class);
    }
}
