package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.repository.CustomerFavoriteRepository;
import com.vmware.retail.analytics.repository.ProductRepository;
import com.vmware.retail.domain.CustomerFavorites;
import com.vmware.retail.domain.customer.CustomerIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import org.springframework.data.redis.core.RedisTemplate;

@Component
public class CalculateFavoritesConsumer implements Consumer<CustomerIdentifier> {


        private final CustomerFavoriteRepository customerFavoriteRepository;
        private final ProductRepository productRepository;
        private final int topCount;

    public CalculateFavoritesConsumer(CustomerFavoriteRepository customerFavoriteRepository,
                                      ProductRepository productRepository,
                                      @Value("${customer.favorites.top.count:3}")
                                      int topCount) {
        this.customerFavoriteRepository = customerFavoriteRepository;
        this.productRepository = productRepository;
        this.topCount = topCount;
    }

    @Override
    public void accept(CustomerIdentifier customerIdentifier) {
        var customerId = customerIdentifier.customerId();
        var customerFavorites = productRepository.findCustomerFavoritesByCustomerIdAndTopCount(customerId, topCount);

        this.customerFavoriteRepository.save(customerFavorites);
    }

    protected String toCustomerIdKey(String customerId) {
        return new StringBuilder().append(CustomerFavorites.class.getName())
                .append(":")
                .append(customerId)
                .toString();
    }
}
