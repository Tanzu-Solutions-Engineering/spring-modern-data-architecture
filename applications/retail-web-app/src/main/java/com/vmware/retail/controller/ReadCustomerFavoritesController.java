/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.controller;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

/**
 * CustomerFavoritesReactiveController
 *
 * @author Gregory Green
 */
@RestController
@RequestMapping(value = "customer/favorites", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public class ReadCustomerFavoritesController
{

    private final CustomerFavoriteRepository repository;

    private final ThreadFactory factory;

    private static final Logger logger = LoggerFactory.getLogger(ReadCustomerFavoritesController.class);

    @Value("${retail.favorites.refresh.rateSeconds}")
    private long refreshRateSecs = 5;

    public ReadCustomerFavoritesController(CustomerFavoriteRepository repository,
                                           @Qualifier("webSocketThreadFactory")
                                       ThreadFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @GetMapping("favorite/{id}")
    public Flux<ServerSentEvent<CustomerFavorites>> streamEvents(@PathVariable String id) {
        logger.info("id: {}",id);

        Scheduler scheduler = Schedulers.newParallel(5,factory);
        return Flux.interval(Duration.ofSeconds(refreshRateSecs),scheduler)
                .map(sequence -> ServerSentEvent.<CustomerFavorites> builder()
                        .data(repository.findById(id).orElse(null))
                        .build());
    }
}