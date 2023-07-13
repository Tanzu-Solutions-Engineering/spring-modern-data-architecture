/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.creational;

import com.rabbitmq.stream.EnvironmentBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RabbitStreamEnvCreateDirector {
    public void construct(Environment springEnv, EnvironmentBuilder rabbitEnvBuilder) {

//        var jsonTree = mapper.readTree(vcapJson);
//
//        var rabbitMq = jsonTree.get("p.rabbitmq")
//                .get(0).get("credentials");;
//
//        var username = rabbitMq.get("username");
//        var password = rabbitMq.get("password");
//        var hostname = rabbitMq.get("hostname");
//        var uris = rabbitMq.get("uris");

    }
}
