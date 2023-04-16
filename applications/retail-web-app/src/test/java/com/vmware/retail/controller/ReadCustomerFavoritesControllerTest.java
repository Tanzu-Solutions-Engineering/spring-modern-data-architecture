/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.controller;

import com.vmware.retail.repository.CustomerFavoriteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReadCustomerFavoritesControllerTest {

    @Mock
    private CustomerFavoriteRepository repository;
    @Mock
    private ThreadFactory factory = Executors.defaultThreadFactory();
    private String id  = "junit";

    @Test
    void streamEvents() {
        var subject = new ReadCustomerFavoritesController(repository,factory);

        var actual = subject.streamEvents(id);

        assertNotNull(actual);

    }
}