package com.vmware.retail.controller;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
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
@RequestMapping("customer/favorites")
public class CustomerFavoritesController
{

    private final CustomerFavoriteRepository repository;


    private final ThreadFactory factory;


    private static final Logger logger = LoggerFactory.getLogger(CustomerFavoritesController.class);

    public CustomerFavoritesController(CustomerFavoriteRepository repository,
                                       @Qualifier("webSocketThreadFactory")
                                       ThreadFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @GetMapping(value = "favorite/{id}", produces = "text/event-stream")
    @Async("taskExecutor")
    public Flux<ServerSentEvent<CustomerFavorites>> findById(@PathVariable String id)
    {
        Scheduler scheduler = Schedulers.newParallel(5,factory);
        return Flux.interval(Duration.ofSeconds(5),scheduler)

                .map(sequence -> {
                            var favorites = repository.findById(id).orElse(null);
                            logger.info("favorites: {}",favorites);
                            var builder = ServerSentEvent
                                    .builder(favorites);
                            return builder.build();
                        }
                );
    }

    @PostMapping("favorite")
    public void saveCustomerFavorites(@RequestBody CustomerFavorites customerFavorites)
    {
        repository.save(customerFavorites);
    }
}