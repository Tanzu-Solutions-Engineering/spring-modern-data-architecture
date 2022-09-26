package com.vmware.retail.controller;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * CustomerFavoritesReactiveController
 *
 * @author Gregory Green
 */
@RestController
@RequestMapping("customer/favorites")
public record CustomerFavoritesController(CustomerFavoriteRepository repository)
{

    @GetMapping("favorite/{id}")
    public Mono<CustomerFavorites> findById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping("favorite")
    public void saveCustomerFavorites(@RequestBody CustomerFavorites customerFavorites)
    {
        repository.saveCustomerFavorites(customerFavorites);
    }
}
