/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.events;

import com.vmware.retail.domain.Promotion;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.EntryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheListerConsumerBridgeTest {

    private CacheListerConsumerBridge<String, Promotion> subject;
    @Mock
    private EntryEvent<String, Promotion> event;
    @Mock
    private Consumer<Promotion> consumer;

    private Promotion promotion = JavaBeanGeneratorCreator.of(Promotion.class).create();

    @BeforeEach
    void setUp() {
        subject = new CacheListerConsumerBridge(consumer);
    }

    @Test
    void afterCreate() {
        when(event.getNewValue()).thenReturn(promotion);

        subject.afterCreate(event);

        verify(consumer).accept(any(Promotion.class));
    }

    @Test
    void afterUpdate() {
        when(event.getNewValue()).thenReturn(promotion);

        subject.afterUpdate(event);

        verify(consumer).accept(any(Promotion.class));
    }
}