package com.vmware.retail.controller;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * CustomerFavoritesReactiveController
 *
 * @author Gregory Green
 */
@RestController
@RequestMapping("customer/favorites")
public record CustomerFavoritesController(CustomerFavoriteRepository repository)
{
    private static final Logger logger = LoggerFactory.getLogger(CustomerFavoritesController.class);

    @GetMapping("favorite/{id}")
    public Flux<ServerSentEvent<CustomerFavorites>> findById(@PathVariable String id)
    {
        return Flux.interval(Duration.ofSeconds(5))
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
