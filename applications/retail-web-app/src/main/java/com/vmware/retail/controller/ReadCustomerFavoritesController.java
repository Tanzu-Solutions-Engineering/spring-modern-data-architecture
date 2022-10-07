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
import java.time.LocalTime;
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

    @SneakyThrows
//    @Async("taskExecutor")

    public SseEmitter findBySseId(@PathVariable String id)
    {
        SseEmitter sseEmitter = new SseEmitter();
        sseEmitter.send(SseEmitter.event().data(repository.findById(id)));
        return sseEmitter;
    }//



    public ReadCustomerFavoritesController(CustomerFavoriteRepository repository,
                                           @Qualifier("webSocketThreadFactory")
                                       ThreadFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

//    @GetMapping("/stream-sse")
    @GetMapping("favorite/{id}")
    public Flux<ServerSentEvent<CustomerFavorites>> streamEvents(@PathVariable String id) {
        logger.info("id: {}",id);

        Scheduler scheduler = Schedulers.newParallel(5,factory);
        return Flux.interval(Duration.ofSeconds(1),scheduler)
                .map(sequence -> ServerSentEvent.<CustomerFavorites> builder()
//                        .id(String.valueOf(sequence))
//                        .event("periodic-event")
                        .data(repository.findById(id).orElse(null))
                        .build());
    }

   // @GetMapping(value = "favorite/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //@Async("taskExecutor")
    public Flux<ResponseEntity<ServerSentEvent<CustomerFavorites>>> findById(@PathVariable String id)
    {
        Scheduler scheduler = Schedulers.newParallel(5,factory);
        var flux = Flux.interval(Duration.ofSeconds(5),scheduler)

                .map(sequence -> {
                            var favorites = repository.findById(id).orElse(null);
                            logger.info("favorites: {}",favorites);
                            var builder = ServerSentEvent
                                    .builder(favorites);
                            var sse = builder.build();
                            return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE).body(sse);
                        }
                );

        return flux;
    }

    @PostMapping("favorite")
    public void saveCustomerFavorites(@RequestBody CustomerFavorites customerFavorites)
    {
        repository.save(customerFavorites);
    }
}