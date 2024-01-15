/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.events;

import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;

import java.util.function.Consumer;


/**
 * Calls consumer accept for create/update events
 * @author gregory green
 * @param <Key> the key event
 * @param <Value> the value of the event
 */
public class CacheListerConsumerBridge<Key,Value> extends CacheListenerAdapter<Key,Value> {
    private final Consumer<Value> consumer;

    public CacheListerConsumerBridge(Consumer<Value> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void afterCreate(EntryEvent<Key, Value> event) {
        processEvent(event);
    }
    @Override
    public void afterUpdate(EntryEvent<Key, Value> event) {
        processEvent(event);
    }


    private void processEvent(EntryEvent<Key, Value> event) {
        consumer.accept(event.getNewValue());
    }
}
