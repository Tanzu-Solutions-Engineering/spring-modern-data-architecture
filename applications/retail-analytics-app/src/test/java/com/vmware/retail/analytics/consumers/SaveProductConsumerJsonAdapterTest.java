/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.consumers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveProductConsumerJsonAdapterTest {

    private SaveProductConsumerJsonAdapter subject;
    @Mock
    private SaveProductConsumer consumer;
    private final String json = "[{\"id\":  \"sku1\", \"name\" : \"Peanut butter\"}]";

    @Test
    void accept() {
        subject = new SaveProductConsumerJsonAdapter(consumer);

        subject.accept(json);

        verify(consumer).accept(any(List.class));
    }
}