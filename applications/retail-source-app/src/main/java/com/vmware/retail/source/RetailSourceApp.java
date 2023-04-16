/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.source;

import nyla.solutions.core.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetailSourceApp {

    public static void main(String[] args) {
        Config.loadArgs(args);
        SpringApplication.run(RetailSourceApp.class, args);
    }
}
