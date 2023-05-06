/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.caching.consumers;

import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.repository.CustomerFavoriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public record SaveFavoritesConsumer(CustomerFavoriteRepository customerFavoriteRepository)  implements Consumer<CustomerFavorites> {

    @Override
    public void accept(CustomerFavorites customerFavorites) {

        log.info("Saving favorites: {}",customerFavorites);

        customerFavoriteRepository.save(customerFavorites);
    }

}
