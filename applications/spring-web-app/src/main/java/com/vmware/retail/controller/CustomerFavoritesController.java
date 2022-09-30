package com.vmware.retail.controller;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * CustomerFavoritesReactiveController
 *
 * @author Gregory Green
 */
@RestController
@RequestMapping("customer/favorites")
@RequiredArgsConstructor
public class CustomerFavoritesController
{
    private final CustomerFavoriteRepository repository;
    private final ThreadFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(CustomerFavoritesController.class);

    @GetMapping("favorite/{id}")
    @Async("taskExecutor")
    public Flux<ServerSentEvent<CustomerFavorites>> findById(@PathVariable String id)
    {
        ThreadFactory factory = Executors.defaultThreadFactory();
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