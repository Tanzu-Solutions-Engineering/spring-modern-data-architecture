package com.vmware.retail.controller;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
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
public class SaveCustomerFavoritesController
{

    private final CustomerFavoriteRepository repository;


    private final ThreadFactory factory;


    private static final Logger logger = LoggerFactory.getLogger(SaveCustomerFavoritesController.class);

    public SaveCustomerFavoritesController(CustomerFavoriteRepository repository,
                                           @Qualifier("webSocketThreadFactory")
                                       ThreadFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }


    @PostMapping("favorite")
    public void saveCustomerFavorites(@RequestBody CustomerFavorites customerFavorites)
    {
        repository.save(customerFavorites);
    }
}